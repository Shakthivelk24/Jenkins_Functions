def call(Map config = [:]) {
    def scanPath  = config.scanPath ?: '.'
    def reportDir = config.reportDir ?: 'reports/dependency-check'

    customLog('Running OWASP Dependency Check...')

    // Must match the Name configured in:
    // Manage Jenkins -> Tools -> OWASP Dependency-Check
    def dcHome = tool 'DependencyCheck'

    echo "Dependency Check Home: ${dcHome}"

    if (isUnix()) {
        sh """
         mkdir -p ${reportDir}

           "${dcHome}/bin/dependency-check.sh" \
           --project "Request System" \
           --scan "${scanPath}" \
           --format HTML \
           --format JSON \
           --nvdApiKey "${env.NVD_API_KEY}" \
            --out "${reportDir}"
        """
    } else {
        bat """
          if not exist "${reportDir}" mkdir "${reportDir}"

          "${dcHome}\\bin\\dependency-check.bat" ^
           --project "Request System" ^
           --scan "${scanPath}" ^
            --format HTML ^
           --format JSON ^
            --nvdApiKey "%NVD_API_KEY%" ^
            --out "${reportDir}"
       """
    }

    customLog('Dependency Check completed.')
}
