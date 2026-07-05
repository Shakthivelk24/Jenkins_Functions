def call(){
     sh '''
                npm.cmd --version
                node --version
                trivy --version
                docker --version
                kubectl version --client
        '''
}