def call(Map config = [:]) {

    def targetUrl     = config.targetUrl
    def reportDir     = config.reportDir ?: 'reports/zap'
    def reportName    = config.reportName ?: 'zap-report'
    def dockerNetwork = config.dockerNetwork ?: 'bridge'

    if (!targetUrl) {
        error "targetUrl is required."
    }

    customLog("Running OWASP ZAP scan against ${targetUrl}")

    int status

    if (isUnix()) {

        status = sh(
            script: """
                mkdir -p ${reportDir}

                docker run --rm \
                    --network ${dockerNetwork} \
                    -v \$(pwd)/${reportDir}:/zap/wrk:rw \
                    ghcr.io/zaproxy/zaproxy:stable \
                    zap-full-scan.py \
                        -t ${targetUrl} \
                        -r ${reportName}.html \
                        -J ${reportName}.json \
                        -l WARN \
                        --auto
            """,
            returnStatus: true
        )

    } else {

        status = bat(
            script: """
                if not exist "${reportDir}" mkdir "${reportDir}"

                docker run --rm ^
                    --network ${dockerNetwork} ^
                    -v "%CD%\\${reportDir}:/zap/wrk:rw" ^
                    ghcr.io/zaproxy/zaproxy:stable ^
                    zap-full-scan.py ^
                        -t ${targetUrl} ^
                        -r ${reportName}.html ^
                        -J ${reportName}.json ^
                        -l WARN ^
                        --auto
            """,
            returnStatus: true
        )
    }

    customLog("OWASP ZAP Exit Code: ${status}")

    switch (status) {

        case 0:
            customLog("No vulnerabilities detected.")
            break

        case 1:
            error("OWASP ZAP detected HIGH-RISK vulnerabilities.")

        case 2:
            customLog("OWASP ZAP detected warnings. Review the HTML report.")
            break

        case 3:
            error("OWASP ZAP detected HIGH-RISK vulnerabilities and warnings.")

        default:
            error("OWASP ZAP scan failed with exit code ${status}.")
    }

    customLog("OWASP ZAP scan completed.")
}