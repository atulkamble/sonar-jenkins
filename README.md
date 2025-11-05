Perfect 👍 — here’s an **expanded and production-ready version** of your **SonarQube + Jenkins Docker Compose setup**, now including:

✅ **PostgreSQL database for SonarQube**
✅ **Custom SonarQube configuration for durability & performance**
✅ **Jenkinsfile example integrated with SonarQube Scanner**
✅ **Step-by-step guide for full integration**

---

## 🧩 **Enhanced Directory Structure**

```bash
sonar-jenkins/
├── docker-compose.yml
├── jenkins/
│   └── Jenkinsfile
└── data/
    ├── jenkins/
    ├── sonarqube/
    │   ├── conf/
    │   ├── data/
    │   ├── extensions/
    │   └── logs/
    └── postgres/
```

---

## 🚀 **Full docker-compose.yml**

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15
    container_name: postgres
    restart: unless-stopped
    environment:
      POSTGRES_USER: sonar
      POSTGRES_PASSWORD: sonar
      POSTGRES_DB: sonarqube
    volumes:
      - ./data/postgres:/var/lib/postgresql/data
    networks:
      - sonarnet

  sonarqube:
    image: sonarqube:lts-community
    container_name: sonarqube
    depends_on:
      - postgres
    restart: unless-stopped
    environment:
      SONAR_JDBC_URL: jdbc:postgresql://postgres:5432/sonarqube
      SONAR_JDBC_USERNAME: sonar
      SONAR_JDBC_PASSWORD: sonar
      SONAR_ES_BOOTSTRAP_CHECKS_DISABLE: "true"
    ports:
      - "9000:9000"
    volumes:
      - ./data/sonarqube/conf:/opt/sonarqube/conf
      - ./data/sonarqube/data:/opt/sonarqube/data
      - ./data/sonarqube/extensions:/opt/sonarqube/extensions
      - ./data/sonarqube/logs:/opt/sonarqube/logs
    networks:
      - sonarnet

  jenkins:
    image: jenkins/jenkins:lts
    container_name: jenkins
    restart: unless-stopped
    user: root
    ports:
      - "8080:8080"
      - "50000:50000"
    volumes:
      - ./data/jenkins:/var/jenkins_home
      - /var/run/docker.sock:/var/run/docker.sock
    environment:
      JAVA_OPTS: "-Djenkins.install.runSetupWizard=false"
    depends_on:
      - sonarqube
    networks:
      - sonarnet

networks:
  sonarnet:
    driver: bridge
```

---

## ⚡ **Run Commands**

```bash
# Step 1: Create persistent directories
mkdir -p data/{jenkins,postgres} data/sonarqube/{conf,data,extensions,logs}

# Step 2: Launch all services
docker-compose up -d

# Step 3: Verify containers
docker ps

# Step 4: Access dashboards
# Jenkins:   http://localhost:8080
# SonarQube: http://localhost:9000
```

---

## 🧠 **PostgreSQL Database Configuration**

| Parameter    | Value     |
| ------------ | --------- |
| **DB Name**  | sonarqube |
| **Username** | sonar     |
| **Password** | sonar     |
| **Host**     | postgres  |
| **Port**     | 5432      |

This setup persists SonarQube’s metadata, metrics, and code analysis results — making it suitable for long-term environments.

---

## 🔐 **Initial Login Info**

| Service       | Username                                             | Password |
| ------------- | ---------------------------------------------------- | -------- |
| **SonarQube** | admin                                                | admin    |
| **Jenkins**   | `cat /var/jenkins_home/secrets/initialAdminPassword` |          |

To get the Jenkins admin password:

```bash
docker exec -it jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

---

## ⚙️ **SonarQube Integration Steps in Jenkins**

1. Go to **Manage Jenkins → Manage Plugins → Available**

   * Install **“SonarQube Scanner”** and **“Pipeline Utility Steps”**.
2. Go to **Manage Jenkins → Configure System → SonarQube Servers**

   * Click **Add SonarQube**, name it `sonarqube`
   * URL: `http://sonarqube:9000`
   * Add a **token credential** from SonarQube (`My Account → Security → Tokens`).
3. Go to **Manage Jenkins → Global Tool Configuration → SonarQube Scanner**

   * Install automatically or define manually (`/usr/local/bin/sonar-scanner`).

---

## 🧩 **Jenkinsfile Example (with SonarQube Integration)**

Create a `Jenkinsfile` in your repo:

```groovy
pipeline {
    agent any

    tools {
        maven 'Maven3'  // or use node/python depending on your project
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/atulkamble/helloworld-sonarqube-scanner.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh 'mvn sonar:sonar -Dsonar.projectKey=helloworld -Dsonar.host.url=http://sonarqube:9000'
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}
```

✅ Works for Maven projects
✅ For Python or Node.js, replace the build command with `pytest` or `npm test` and call `sonar-scanner` directly.

---

## 🧰 **Sonar Scanner Command Example (Manual CLI)**

For testing directly inside Jenkins container:

```bash
docker exec -it jenkins bash
sonar-scanner \
  -Dsonar.projectKey=helloworld \
  -Dsonar.sources=. \
  -Dsonar.host.url=http://sonarqube:9000 \
  -Dsonar.login=<your_token>
```

---

## 🧼 **Stop and Clean Up**

```bash
docker-compose down
# To remove volumes too:
docker-compose down -v
```

---

## 📊 **Benefits of This Setup**

| Feature                            | Benefit                                               |
| ---------------------------------- | ----------------------------------------------------- |
| **Dockerized Jenkins + SonarQube** | Portable, quick DevOps lab setup                      |
| **PostgreSQL backend**             | Stable & production-grade                             |
| **Persistent Volumes**             | Data retained across container restarts               |
| **Integrated Pipeline**            | Real CI/CD + Static Analysis                          |
| **Reusable Template**              | Works for any project type: Java, Node.js, Python, Go |

---

Would you like me to expand this with a **GitHub Actions CI/CD workflow** that triggers the same SonarQube analysis automatically from your GitHub repo (instead of Jenkins)?
