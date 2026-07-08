def call(Map config = [:]) {

    def scanPath  = config.scanPath ?: '.'
    def reportDir = config.reportDir ?: 'reports/dependency-check'

    customLog("Running OWASP Dependency Check...")

    if (isUnix()) {

        sh """
            mkdir -p ${reportDir}

            dependency-check.sh \
                --project "Request System" \
                --scan ${scanPath} \
                --format HTML \
                --format JSON \
                --out ${reportDir}
        """

    } else {

        bat """
            if not exist "${reportDir}" mkdir "${reportDir}"

            dependency-check.bat ^
                --project "Request System" ^
                --scan "${scanPath}" ^
                --format HTML ^
                --format JSON ^
                --out "${reportDir}"
        """
    }

    customLog("Dependency Check completed.")
}