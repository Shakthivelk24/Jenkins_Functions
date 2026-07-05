def call(String imageName,
         String imageVersion = env.BUILD_NUMBER) {

    stage('Docker Push') {

        sh """
            docker push ${imageName}:${imageVersion}
            docker push ${imageName}:latest
        """
    }
}