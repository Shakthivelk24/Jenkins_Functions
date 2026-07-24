def call() {
    if (isUnix()) {
        sh '''
            npm --version
            node --version
            trivy --version
            docker --version
            kubectl version --client
        '''
    } else {
        bat '''
            npm --version
            node --version
            trivy --version
            docker --version
            kubectl version --client
        '''
    }
}