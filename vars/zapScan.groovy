def call(Map config = [:]) {

    def targetUrl = config.targetUrl
    def reportDir = config.reportDir ?: 'reports/zap'
    def reportName = config.reportName ?: 'zap-report'
    def dockerNetwork = config.dockerNetwork ?: 'bridge'

    if (!targetUrl) {
        error "targetUrl is required."
    }

    customLog("Running OWASP ZAP scan against ${targetUrl}")

    if (isUnix()) {

        sh """
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
        """

    } else {

        bat """
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
        """
    }

    customLog("OWASP ZAP scan completed.")
}