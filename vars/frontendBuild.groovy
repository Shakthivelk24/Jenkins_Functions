def call(String directory = 'client') {
    dir(directory) {
        if (isUnix()) {
            sh '''
                npm run build

                echo "========== Build Output =========="
                ls -la dist
            '''
        } else {
            bat '''
                npm run build

                echo ========== Build Output ==========
                dir dist
            '''
        }
    }
}
