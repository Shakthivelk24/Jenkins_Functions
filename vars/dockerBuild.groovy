def call(String imageName,
         String dockerfile,
         String context = ".",
         String imageVersion = env.BUILD_NUMBER) {

    stage('Docker Build') {

        sh """
            docker build \
                -t ${imageName}:${imageVersion} \
                -t ${imageName}:latest \
                -f ${dockerfile} \
                ${context}
        """
    }
}