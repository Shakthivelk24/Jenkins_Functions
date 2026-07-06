def call(String imageName,
         String dockerfile,
         String context = ".",
         String imageVersion = env.BUILD_NUMBER) {

    customLog("Building Docker image: ${imageName}:${imageVersion}")

    if (isUnix()) {

        sh """
            docker build \
                -t ${imageName}:${imageVersion} \
                -t ${imageName}:latest \
                -f ${dockerfile} \
                ${context}
        """

    } else {

        bat """
            docker build ^
                -t ${imageName}:${imageVersion} ^
                -t ${imageName}:latest ^
                -f ${dockerfile} ^
                ${context}
        """
    }

    customLog("Docker image built successfully.")
}