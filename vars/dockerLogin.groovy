def call(String credentialsId = 'DockerHub') {
    withCredentials([
        usernamePassword(
            credentialsId: credentialsId,
            usernameVariable: 'DOCKER_USER',
            passwordVariable: 'DOCKER_PASS'
        )
    ]) {
        customLog('Logging into Docker Hub...')

        if (isUnix()) {
            sh '''
               echo "$DOCKER_PASS" | docker login \
               -u "$DOCKER_USER" \
               --password-stdin
            '''
        } else {
            bat '''
             @echo off
             echo|set /p="%DOCKER_PASS%" | docker login -u %DOCKER_USER% --password-stdin
             '''
        }
        customLog('Docker login successful.')
    }
}
