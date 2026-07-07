def call(Map config = [:]) {

    def namespace      = config.namespace ?: 'default'
    def frontendImage  = config.frontendImage
    def backendImage   = config.backendImage
    def imageTag       = config.imageTag ?: env.BUILD_NUMBER

    def templateFile   = config.templateFile ?: 'kubernetes/secret.yaml.template'
    def secretFile     = config.secretFile ?: 'kubernetes/secret.yaml'

    if (!frontendImage || !backendImage) {
        error "frontendImage and backendImage are required."
    }

    customLog("Deploying application to Kubernetes...")

    withCredentials([
        string(credentialsId: 'JWT_SECRET', variable: 'JWT_SECRET'),
        string(credentialsId: 'MONGODB_URL', variable: 'MONGODB_URL')
    ]) {

        // =====================================================
        // Generate Kubernetes Secret (Cross-platform)
        // =====================================================

        def template = readFile(file: templateFile)

        template = template
            .replace("__JWT_SECRET__", env.JWT_SECRET)
            .replace("__MONGODB_URL__", env.MONGODB_URL)

        writeFile(
            file: secretFile,
            text: template
        )

        if (isUnix()) {

            sh """
                kubectl apply -f k8s/namespace.yaml
                kubectl apply -f k8s/configmap.yaml
                kubectl apply -f ${secretFile}

                kubectl apply -f k8s/backend-deployment.yaml
                kubectl apply -f k8s/backend-service.yaml

                kubectl apply -f k8s/frontend-deployment.yaml
                kubectl apply -f k8s/frontend-service.yaml

                kubectl apply -f k8s/ingress.yaml

                kubectl set image deployment/frontend \
                    frontend=${frontendImage}:${imageTag} \
                    -n ${namespace}

                kubectl set image deployment/backend \
                    backend=${backendImage}:${imageTag} \
                    -n ${namespace}

                kubectl rollout status deployment/frontend \
                    -n ${namespace} \
                    --timeout=300s

                kubectl rollout status deployment/backend \
                    -n ${namespace} \
                    --timeout=300s
            """

        } else {

            bat """
                kubectl apply -f k8s\\namespace.yaml
                kubectl apply -f k8s\\configmap.yaml
                kubectl apply -f ${secretFile}

                kubectl apply -f k8s\\backend-deployment.yaml
                kubectl apply -f k8s\\backend-service.yaml

                kubectl apply -f k8s\\frontend-deployment.yaml
                kubectl apply -f k8s\\frontend-service.yaml

                kubectl apply -f k8s\\ingress.yaml

                kubectl set image deployment/frontend ^
                    frontend=${frontendImage}:${imageTag} ^
                    -n ${namespace}

                kubectl set image deployment/backend ^
                    backend=${backendImage}:${imageTag} ^
                    -n ${namespace}

                kubectl rollout status deployment/frontend ^
                    -n ${namespace} ^
                    --timeout=300s

                kubectl rollout status deployment/backend ^
                    -n ${namespace} ^
                    --timeout=300s
            """
        }
    }

    customLog("Application deployed successfully.")
}