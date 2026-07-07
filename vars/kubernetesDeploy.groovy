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

        if (isUnix()) {

            sh """
                # ==========================================
                # Generate Kubernetes Secret
                # ==========================================

                sed \
                    -e "s|__JWT_SECRET__|\${JWT_SECRET}|g" \
                    -e "s|__MONGODB_URL__|\${MONGODB_URL}|g" \
                    ${templateFile} > ${secretFile}

                # ==========================================
                # Apply Kubernetes Resources
                # ==========================================

                kubectl apply -f kubernetes/namespace.yaml
                kubectl apply -f kubernetes/configmap.yaml
                kubectl apply -f ${secretFile}

                kubectl apply -f kubernetes/backend-deployment.yaml
                kubectl apply -f kubernetes/backend-service.yaml

                kubectl apply -f kubernetes/frontend-deployment.yaml
                kubectl apply -f kubernetes/frontend-service.yaml

                kubectl apply -f kubernetes/ingress.yaml

                # ==========================================
                # Update Images
                # ==========================================

                kubectl set image deployment/frontend \
                    frontend=${frontendImage}:${imageTag} \
                    -n ${namespace}

                kubectl set image deployment/backend \
                    backend=${backendImage}:${imageTag} \
                    -n ${namespace}

                # ==========================================
                # Wait for Rollout
                # ==========================================

                kubectl rollout status deployment/frontend \
                    -n ${namespace} \
                    --timeout=300s

                kubectl rollout status deployment/backend \
                    -n ${namespace} \
                    --timeout=300s
            """

        } else {

            bat """
                powershell -Command ^
                "(Get-Content '${templateFile}') ^
                -replace '__JWT_SECRET__','%JWT_SECRET%' ^
                -replace '__MONGODB_URL__','%MONGODB_URL%' ^
                | Set-Content '${secretFile}'"

                kubectl apply -f kubernetes\\namespace.yaml
                kubectl apply -f kubernetes\\configmap.yaml
                kubectl apply -f ${secretFile}

                kubectl apply -f kubernetes\\backend-deployment.yaml
                kubectl apply -f kubernetes\\backend-service.yaml

                kubectl apply -f kubernetes\\frontend-deployment.yaml
                kubectl apply -f kubernetes\\frontend-service.yaml

                kubectl apply -f kubernetes\\ingress.yaml

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