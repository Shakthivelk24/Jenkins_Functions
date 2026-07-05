def call(String directory,
         String framework,
         String coverageDir,
         String junitFile = '') {

    dir(directory) {
        customLog("Running tests in ${directory}")

        if (framework == 'vitest') {
            if (isUnix()) {
                sh '''
                    npm test -- --run --coverage
                '''
            } else {
                bat '''
                    npm test -- --run --coverage
                '''
            }
        } else if (framework == 'jest') {
            if (isUnix()) {
                sh '''
                    npm test -- \
                        --coverage \
                        --passWithNoTests
                '''
            } else {
                bat '''
                    npm test -- ^
                        --coverage ^
                        --passWithNoTests
                '''
            }
        }

        if (junitFile?.trim()) {
            junit(
                allowEmptyResults: true,
                testResults: junitFile
            )
        }
    }
}
