def call(String namespace = 'default') {

    customLog("Verifying Kubernetes deployment...")

    if (isUnix()) {

        sh """
            echo "========== Pods =========="
            kubectl get pods -n ${namespace} -o wide

            echo ""
            echo "========== Services =========="
            kubectl get svc -n ${namespace}

            echo ""
            echo "========== Endpoints =========="
            kubectl get endpoints -n ${namespace}

            echo ""
            echo "========== Ingress =========="
            kubectl get ingress -n ${namespace}

            echo ""
            echo "Deployment verification completed successfully."
        """

    } else {

        bat """
            echo ========= Pods =========
            kubectl get pods -n ${namespace} -o wide

            echo.
            echo ========= Services =========
            kubectl get svc -n ${namespace}

            echo.
            echo ========= Endpoints =========
            kubectl get endpoints -n ${namespace}

            echo.
            echo ========= Ingress =========
            kubectl get ingress -n ${namespace}

            echo.
            echo Deployment verification completed successfully.
        """
    }

    customLog("Verification completed.")
}