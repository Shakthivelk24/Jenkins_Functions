# 🚀 Jenkins Functions (Shared Library)

<p align="center">
  <img src="https://img.shields.io/badge/Jenkins-Shared%20Library-D24939?style=for-the-badge&logo=jenkins&logoColor=white">
  <img src="https://img.shields.io/badge/Groovy-Automation-4298B8?style=for-the-badge&logo=apachegroovy&logoColor=white">
  <img src="https://img.shields.io/badge/DevSecOps-CI%2FCD-blue?style=for-the-badge">
  <img src="https://img.shields.io/badge/Kubernetes-Deployment-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white">
</p>

---

# 📖 About

This repository contains a **Jenkins Shared Library** developed to simplify and standardize CI/CD pipelines for DevOps and DevSecOps projects.

Instead of writing the same pipeline logic repeatedly, reusable Groovy functions are stored in this shared library and imported into Jenkins pipelines. This approach makes pipelines cleaner, easier to maintain, and promotes code reusability across multiple projects.

---

# 📂 Repository Structure

```
Jenkins_Functions/
│
├── vars/
│   ├── archiveReports.groovy
│   ├── backendValidate.groovy
│   ├── check.groovy
│   ├── customLog.groovy
│   ├── dockerBuild.groovy
│   ├── dockerLogin.groovy
│   ├── dockerPush.groovy
│   ├── frontendBuild.groovy
│   ├── kubernetesDeploy.groovy
│   ├── npmInstall.groovy
│   ├── npmTest.groovy
│   ├── qualityGate.groovy
│   ├── runDependencyCheck.groovy
│   ├── sonarqubeScan.groovy
│   ├── trivyScan.groovy
│   ├── trivySecretScan.groovy
│   ├── verifyDeployment.groovy
│   └── zapScan.groovy
│
└── README.md
```

---

# ✨ Features

- Reusable Jenkins Shared Library
- Modular Pipeline Functions
- Docker Automation
- Kubernetes Deployment
- SonarQube Integration
- OWASP Dependency Check
- Trivy Vulnerability Scanning
- Trivy Secret Scanning
- OWASP ZAP Security Testing
- Build Verification
- Report Archiving
- Custom Logging Utilities

---

# 📚 Available Functions

| Function | Description |
|----------|-------------|
| `archiveReports()` | Archive scan reports and build artifacts |
| `backendValidate()` | Validate backend project structure and configuration |
| `check()` | Verify required tools and versions |
| `customLog()` | Print formatted pipeline messages |
| `dockerBuild()` | Build Docker images |
| `dockerLogin()` | Authenticate with Docker Registry |
| `dockerPush()` | Push Docker images |
| `frontendBuild()` | Build frontend applications |
| `kubernetesDeploy()` | Deploy application to Kubernetes |
| `npmInstall()` | Install Node.js dependencies |
| `npmTest()` | Execute npm test cases |
| `qualityGate()` | Wait for SonarQube Quality Gate |
| `runDependencyCheck()` | Run OWASP Dependency Check |
| `sonarqubeScan()` | Perform SonarQube analysis |
| `trivyScan()` | Scan Docker images for vulnerabilities |
| `trivySecretScan()` | Scan repository for exposed secrets |
| `verifyDeployment()` | Verify Kubernetes deployment status |
| `zapScan()` | Run OWASP ZAP security scan |

---

# 🛠 Technologies Used

| Technology | Purpose |
|------------|----------|
| Jenkins | CI/CD Automation |
| Groovy | Shared Library Development |
| Docker | Containerization |
| Kubernetes | Container Orchestration |
| SonarQube | Code Quality Analysis |
| Trivy | Vulnerability & Secret Scanning |
| OWASP Dependency Check | Dependency Security Analysis |
| OWASP ZAP | Dynamic Application Security Testing |
| Node.js | Frontend Builds |
| Git | Version Control |

---

# 📌 Prerequisites

- Jenkins
- Git
- Docker
- Kubernetes
- Java
- Groovy
- Node.js
- SonarQube Server
- Trivy
- OWASP Dependency Check
- OWASP ZAP

---

# 🚀 Using the Shared Library

## Register the Shared Library

1. Open **Jenkins Dashboard**
2. Navigate to:

```
Manage Jenkins
    └── System
          └── Global Pipeline Libraries
```

3. Add a new library.

Example:

```
Name:
jenkins-functions

Default Version:
main

Retrieval Method:
Modern SCM

SCM:
Git

Repository:
https://github.com/Shakthivelk24/Jenkins_Functions.git
```

---

## Import the Library

```groovy
@Library('jenkins-functions') _
```

---

## Example Pipeline

```groovy
@Library('jenkins-functions') _

pipeline {
    agent any

    stages {

        stage('Install Dependencies') {
            steps {
                npmInstall()
            }
        }

        stage('Build Docker Image') {
            steps {
                dockerBuild()
            }
        }

        stage('Push Docker Image') {
            steps {
                dockerPush()
            }
        }

        stage('SonarQube Scan') {
            steps {
                sonarqubeScan()
            }
        }

        stage('Quality Gate') {
            steps {
                qualityGate()
            }
        }

        stage('Trivy Scan') {
            steps {
                trivyScan()
            }
        }

        stage('Deploy') {
            steps {
                kubernetesDeploy()
            }
        }

        stage('Verify Deployment') {
            steps {
                verifyDeployment()
            }
        }
    }
}
```

---

# 📈 Pipeline Workflow

```
Source Code
      │
      ▼
Install Dependencies
      │
      ▼
Build Application
      │
      ▼
SonarQube Analysis
      │
      ▼
Quality Gate
      │
      ▼
Dependency Check
      │
      ▼
Docker Build
      │
      ▼
Trivy Scan
      │
      ▼
Docker Push
      │
      ▼
Kubernetes Deployment
      │
      ▼
Verify Deployment
      │
      ▼
OWASP ZAP Scan
      │
      ▼
Archive Reports
```

---

# 🎯 Advantages

- Code Reusability
- Cleaner Jenkinsfiles
- Centralized Pipeline Logic
- Easier Maintenance
- Consistent CI/CD Workflows
- Improved Security Automation
- Reduced Pipeline Duplication
- Faster Development

---

# 🤝 Contributing

Contributions are welcome!

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push the branch
5. Create a Pull Request

---

# 👨‍💻 Author

**Shakthi Vel K**

📧 Email: shakthivelk1124@example.com

🔗 GitHub: https://github.com/Shakthivelk24

🔗 LinkedIn: https://www.linkedin.com/in/shakthi-vel-k-b35484343/

---

# ⭐ Support

If you found this repository useful,

⭐ Star the repository

🍴 Fork the repository

📢 Share it with others

---

<p align="center">
Built with ❤️ to simplify Jenkins CI/CD pipelines using Shared Libraries.
</p>