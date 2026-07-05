def call(Map config = [:]) {

    def scannerName = config.scannerName ?: 'SonarScanner'
    def sonarServer = config.sonarServer ?: 'SonarQube'
    def projectKey = config.projectKey
    def projectName = config.projectName
    def projectVersion = config.projectVersion ?: env.BUILD_NUMBER
    def sources = config.sources ?: '.'
    def exclusions = config.exclusions ?: '**/node_modules/**,**/dist/**,**/coverage/**'

    stage('🔍 SonarQube Scan') {

        def scannerHome = tool scannerName

        withSonarQubeEnv(sonarServer) {

            sh """
                ${scannerHome}/bin/sonar-scanner \
                    -Dsonar.projectKey=${projectKey} \
                    -Dsonar.projectName="${projectName}" \
                    -Dsonar.projectVersion=${projectVersion} \
                    -Dsonar.sources=${sources} \
                    -Dsonar.exclusions=${exclusions}
            """
        }
    }
}