def call(int timeoutMinutes = 10) {

    stage('✅ Quality Gate') {

        echo "=== STAGE: Checking SonarQube Quality Gate ==="

        timeout(time: timeoutMinutes, unit: 'MINUTES') {
            waitForQualityGate abortPipeline: true
        }

        echo "Quality Gate PASSED ✓"
    }
}