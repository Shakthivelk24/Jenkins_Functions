def call(String directory) {

    dir(directory) {

        customLog("Installing dependencies in ${directory}")

        if (isUnix()) {
            sh '''
                npm ci
            '''
        } else {
            bat '''
                npm ci
            '''
        }
    }
}