def call(Map config = [:]) {
    def scanPath  = config.scanPath ?: '.'
    def reportDir = config.reportDir ?: 'reports/trivy'

    customLog('Scanning source code for hardcoded secrets...')

    if (isUnix()) {
        sh """
            mkdir -p ${reportDir}

            # JSON Report
            trivy fs \
                --scanners secret \
                --exit-code 0 \
                --format json \
                --output ${reportDir}/secret-scan.json \
                ${scanPath}

            # HTML Report
            trivy fs \
                --scanners secret \
                --exit-code 0 \
                --format template \
                --template "@tools/trivy/html.tpl" \
                --output ${reportDir}/secret-scan.html \
                ${scanPath}

            # Console Report + Security Gate
            trivy fs \
                --scanners secret \
                --exit-code 1 \
                --format table \
                ${scanPath}
        """
    } else {
        bat """
            if not exist "${reportDir}" mkdir "${reportDir}"

            trivy fs ^
                --scanners secret ^
                --exit-code 0 ^
                --format json ^
                --output "${reportDir}\\secret-scan.json" ^
                "${scanPath}"

            trivy fs ^
                --scanners secret ^
                --exit-code 0 ^
                --format template ^
                --template "@tools/trivy/html.tpl" ^
                --output "${reportDir}\\secret-scan.html" ^
                "${scanPath}"

            trivy fs ^
                --scanners secret ^
                --exit-code 1 ^
                --format table ^
                "${scanPath}"
        """
    }

    customLog('Secret scan completed successfully.')
}
