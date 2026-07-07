def call() {

    customLog("Archiving reports...")

    // ==========================================================
    // Archive Reports
    // ==========================================================
    archiveArtifacts(
        artifacts: '''
            reports/trivy/*.json,
            reports/trivy/*.html,
            reports/zap/*.json,
            reports/zap/*.html,
            coverage/**/*
        ''',
        allowEmptyArchive: true,
        fingerprint: true
    )

    // ==========================================================
    // Trivy Reports
    // ==========================================================
    publishHTML([
        allowMissing: true,
        alwaysLinkToLastBuild: true,
        keepAll: true,
        reportDir: 'reports/trivy',
        reportFiles: 'frontend.html',
        reportName: 'Trivy Frontend Report',
        reportTitles: 'Frontend Vulnerability Report'
    ])

    publishHTML([
        allowMissing: true,
        alwaysLinkToLastBuild: true,
        keepAll: true,
        reportDir: 'reports/trivy',
        reportFiles: 'backend.html',
        reportName: 'Trivy Backend Report',
        reportTitles: 'Backend Vulnerability Report'
    ])

    publishHTML([
        allowMissing: true,
        alwaysLinkToLastBuild: true,
        keepAll: true,
        reportDir: 'reports/trivy',
        reportFiles: 'secret-scan.html',
        reportName: 'Trivy Secret Scan Report',
        reportTitles: 'Secret Scan Report'
    ])

    // ==========================================================
    // OWASP ZAP Reports
    // ==========================================================
    publishHTML([
        allowMissing: true,
        alwaysLinkToLastBuild: true,
        keepAll: true,
        reportDir: 'reports/zap',
        reportFiles: 'frontend-scan.html',
        reportName: 'OWASP ZAP Frontend Report',
        reportTitles: 'Frontend Security Report'
    ])

    publishHTML([
        allowMissing: true,
        alwaysLinkToLastBuild: true,
        keepAll: true,
        reportDir: 'reports/zap',
        reportFiles: 'backend-api-scan.html',
        reportName: 'OWASP ZAP Backend API Report',
        reportTitles: 'Backend API Security Report'
    ])

    // ==========================================================
    // Frontend Coverage Report
    // ==========================================================
    publishHTML([
        allowMissing: true,
        alwaysLinkToLastBuild: true,
        keepAll: true,
        reportDir: 'coverage/frontend',
        reportFiles: 'index.html',
        reportName: 'Frontend Test Coverage',
        reportTitles: 'Frontend Coverage Report'
    ])

    // ==========================================================
    // Backend Coverage Report
    // ==========================================================
    publishHTML([
        allowMissing: true,
        alwaysLinkToLastBuild: true,
        keepAll: true,
        reportDir: 'coverage/backend',
        reportFiles: 'index.html',
        reportName: 'Backend Test Coverage',
        reportTitles: 'Backend Coverage Report'
    ])

    customLog("Reports archived successfully.")
}