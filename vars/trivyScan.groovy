def call(Map config = [:]) {
    def imageName    = config.imageName
    def imageVersion = config.imageVersion ?: env.BUILD_NUMBER
    def reportDir    = config.reportDir ?: 'reports/trivy'
    def reportName   = config.reportName ?: 'trivy'

    if (!imageName) {
        error "imageName is required."
    }

    customLog("Scanning ${imageName}:${imageVersion}")

    if (isUnix()) {
        sh """
            mkdir -p ${reportDir}

            # JSON Report
            trivy image \
                --exit-code 0 \
                --ignore-unfixed \
                --severity LOW,MEDIUM,HIGH,CRITICAL \
                --pkg-types os,library
                --format json \
                --output ${reportDir}/${reportName}.json \
                ${imageName}:${imageVersion}

            # HTML Report
            trivy image \
                --exit-code 0 \
                --ignore-unfixed \
                --severity LOW,MEDIUM,HIGH,CRITICAL \
                --pkg-types os,library \
                --format template \
                --template "@tools/trivy/html.tpl" \
                --output ${reportDir}/${reportName}.html \
                ${imageName}:${imageVersion}

            # Console Report + Security Gate
            trivy image \
                --exit-code 1 \
                --ignore-unfixed \
                --severity CRITICAL \
                --pkg-types os,library \
                --format table \
                ${imageName}:${imageVersion}
        """
    } else {
        bat """
            if not exist "${reportDir}" mkdir "${reportDir}"

            trivy image ^
                --exit-code 0 ^
                --ignore-unfixed ^
                --severity LOW,MEDIUM,HIGH,CRITICAL ^
                --pkg-types os,library ^
                --format json ^
                --output "${reportDir}\\${reportName}.json" ^
                ${imageName}:${imageVersion}

            trivy image ^
                --exit-code 0 ^
                --ignore-unfixed ^
                --severity LOW,MEDIUM,HIGH,CRITICAL ^
                --pkg-types os,library ^
                --format template ^
                --template "@tools/trivy/html.tpl" ^
                --output "${reportDir}\\${reportName}.html" ^
                ${imageName}:${imageVersion}

            trivy image ^
                --exit-code 1 ^
                --ignore-unfixed ^
                --severity CRITICAL ^
                --pkg-types os,library ^
                --format table ^
                ${imageName}:${imageVersion}
        """
    }

    customLog('Trivy scan completed.')
}
