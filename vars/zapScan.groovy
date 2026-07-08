def call(Map config = [:]) {
    def targetUrl     = config.targetUrl
    def reportDir     = config.reportDir ?: 'reports/zap'
    def reportName    = config.reportName ?: 'zap-report'
    def dockerNetwork = config.dockerNetwork ?: 'bridge'

    if (!targetUrl) {
        error 'targetUrl is required.'
    }

    customLog("Running OWASP ZAP Baseline Scan against ${targetUrl}")

    int status

    if (isUnix()) {
        status = sh(
            script: """
                mkdir -p ${reportDir}

                docker run --rm \
                    --network ${dockerNetwork} \
                    -v \$(pwd)/${reportDir}:/zap/wrk:rw \
                    ghcr.io/zaproxy/zaproxy:stable \
                    zap-baseline.py \
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
                    zap-baseline.py ^
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
            customLog('No security issues detected.')
            break

    case 1:
            error('OWASP ZAP detected FAIL-level vulnerabilities.')

    case 2:
            customLog('OWASP ZAP detected WARN-level issues. Review the HTML report.')
            break

    case 3:
            error('OWASP ZAP detected FAIL and WARN-level vulnerabilities.')

    default:
        error("OWASP ZAP scan execution failed. Exit Code: ${status}")
    }
    customLog('OWASP ZAP scan completed.')
}
