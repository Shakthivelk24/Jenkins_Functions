def call(int timeoutMinutes = 10) {
    echo '=== Checking SonarQube Quality Gate ==='

    timeout(time: timeoutMinutes, unit: 'MINUTES') {
        waitForQualityGate abortPipeline: true
    }

    echo 'Quality Gate PASSED ✓'
}
