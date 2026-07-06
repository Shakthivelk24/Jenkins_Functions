def call(String imageName,
         String imageVersion = env.BUILD_NUMBER) {

    customLog("Pushing Docker image: ${imageName}:${imageVersion}")

    if (isUnix()) {
        sh """
            docker push ${imageName}:${imageVersion}
            docker push ${imageName}:latest
        """
    } else {
        bat """
            docker push ${imageName}:${imageVersion}
            docker push ${imageName}:latest
        """
    }
    customLog('Docker image pushed successfully.')
}
