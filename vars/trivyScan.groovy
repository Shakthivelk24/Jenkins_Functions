def call(Map config = [:]) {

    def imageName    = config.imageName
    def imageVersion = config.imageVersion ?: env.BUILD_NUMBER
    def reportDir    = config.reportDir ?: 'reports/trivy'
    def reportName   = config.reportName ?: 'trivy'

    if (!imageName) {
        error "imageName is required."
    }

    def cacheDir = "${reportDir}/cache-${reportName}"

    customLog("Scanning ${imageName}:${imageVersion}")

    if (isUnix()) {

        sh """
            mkdir -p "${reportDir}"
            mkdir -p "${cacheDir}"

            trivy image \
                --download-db-only \
                --cache-dir "${cacheDir}" \
                --skip-version-check

            COMMON="--cache-dir ${cacheDir} \
                    --skip-version-check \
                    --scanners vuln \
                    --ignore-unfixed \
                    --pkg-types os,library"

            # HTML Report
            trivy image \$COMMON \
                --exit-code 0 \
                --severity LOW,MEDIUM,HIGH,CRITICAL \
                --format template \
                --template "@tools/trivy/html.tpl" \
                --output "${reportDir}/${reportName}.html" \
                ${imageName}:${imageVersion}

            # Security Gate
            trivy image \$COMMON \
                --exit-code 1 \
                --severity CRITICAL \
                --format table \
                ${imageName}:${imageVersion}
        """

    } else {

        bat """
            if not exist "${reportDir}" mkdir "${reportDir}"
            if not exist "${reportDir}\\cache-${reportName}" mkdir "${reportDir}\\cache-${reportName}"

            trivy image ^
                --download-db-only ^
                --cache-dir "${reportDir}\\cache-${reportName}" ^
                --skip-version-check

            set TRIVY_COMMON=--cache-dir "${reportDir}\\cache-${reportName}" --skip-version-check --scanners vuln --ignore-unfixed --pkg-types os,library

            rem HTML Report
            trivy image ^
                %TRIVY_COMMON% ^
                --exit-code 0 ^
                --severity LOW,MEDIUM,HIGH,CRITICAL ^
                --format template ^
                --template "@tools/trivy/html.tpl" ^
                --output "${reportDir}\\${reportName}.html" ^
                ${imageName}:${imageVersion}

            rem Security Gate
            trivy image ^
                %TRIVY_COMMON% ^
                --exit-code 1 ^
                --severity CRITICAL ^
                --format table ^
                ${imageName}:${imageVersion}
        """
    }

    customLog("Trivy scan completed.")
}