def call(String directory,
         String framework = 'vitest',
         String coverageDir = 'coverage',
         String junitFile = '') {

    dir(directory) {

        customLog("Running ${framework} tests in ${directory}")

        if (framework == 'vitest') {

            if (isUnix()) {
                sh """
                   npm run coverage
                """
            } else {
                bat """
                    npm run coverage
                """
            }

        } else if (framework == 'jest') {

            if (isUnix()) {
                sh """
                    npm test -- --coverage --passWithNoTests
                """
            } else {
                bat """
                    npm test -- --coverage --passWithNoTests
                """
            }
        }

        if (junitFile?.trim()) {
            junit(
                allowEmptyResults: true,
                testResults: junitFile
            )
        }

        if (fileExists("${coverageDir}/lcov.info")) {
            customLog("Coverage report found: ${coverageDir}/lcov.info")
        } else {
            customLog("WARNING: Coverage report NOT found!")
        }
    }
}