def call(String directory,
         String coverageDir,
         String junitFile) {

    dir(directory) {

        customLog("Running tests in ${directory}")

        if (isUnix()) {

            sh """
                npm test -- \
                    --ci \
                    --coverage \
                    --watchAll=false \
                    --coverageDirectory=${coverageDir} || true
            """

        } else {

            bat """
                npm test -- ^
                    --ci ^
                    --coverage ^
                    --watchAll=false ^
                    --coverageDirectory=${coverageDir}
            """
        }

        junit allowEmptyResults: true,
              testResults: junitFile
    }
}