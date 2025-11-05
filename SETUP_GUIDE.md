# SonarQube + Jenkins Integration Setup Guide

## 📋 Prerequisites

- Docker and Docker Compose installed
- At least 4GB of RAM available
- Ports 8080, 9000, and 5432 available

## 🚀 Quick Start

### 1. Start the Services

```bash
# Make the setup script executable
chmod +x setup.sh

# Run the setup script
./setup.sh
```

Or manually:

```bash
# Create data directories
mkdir -p data/{jenkins,postgres}
mkdir -p data/sonarqube/{conf,data,extensions,logs}

# Start services
docker-compose up -d
```

### 2. Access the Services

- **SonarQube**: http://localhost:9000
  - Default credentials: `admin` / `admin`
  - You'll be prompted to change the password on first login

- **Jenkins**: http://localhost:8081
  - Get initial admin password: `docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword`

## 🔧 Configuration Steps

### A. Configure SonarQube

1. **Login to SonarQube** (http://localhost:9000)
   - Username: `admin`
   - Password: `admin` (change it on first login)

2. **Create a Token**
   - Go to: User → My Account → Security → Generate Tokens
   - Name: `jenkins`
   - Type: `Global Analysis Token`
   - Copy the generated token (you'll need it for Jenkins)

3. **Create a Project**
   - Click "Create Project" → "Manually"
   - Project key: `my-java-project`
   - Display name: `My Java Project`

### B. Configure Jenkins

1. **Access Jenkins** (http://localhost:8081)
   - Get initial password: 
     ```bash
     docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
     ```

2. **Install Required Plugins**
   - Go to: Manage Jenkins → Manage Plugins → Available
   - Install:
     - SonarQube Scanner
     - Maven Integration
     - JUnit
     - JaCoCo

3. **Configure SonarQube Server**
   - Go to: Manage Jenkins → Configure System
   - Find "SonarQube servers" section
   - Click "Add SonarQube"
     - Name: `SonarQube`
     - Server URL: `http://sonarqube:9000`
     - Server authentication token: Add the token from SonarQube
       - Kind: Secret text
       - Secret: [paste the token]
       - ID: `sonarqube-token`

4. **Configure SonarQube Scanner**
   - Go to: Manage Jenkins → Global Tool Configuration
   - Find "SonarQube Scanner" section
   - Click "Add SonarQube Scanner"
     - Name: `SonarScanner`
     - Install automatically: ✓
     - Version: Latest

5. **Configure Maven**
   - In Global Tool Configuration
   - Find "Maven" section
   - Click "Add Maven"
     - Name: `Maven-3.9`
     - Install automatically: ✓
     - Version: 3.9.x

6. **Configure JDK**
   - In Global Tool Configuration
   - Find "JDK" section
   - Click "Add JDK"
     - Name: `JDK-17`
     - Install automatically: ✓
     - Version: Java 17

### C. Create a Jenkins Pipeline

1. **Create New Pipeline Job**
   - Click "New Item"
   - Enter name: `SonarQube-Demo`
   - Select: "Pipeline"
   - Click "OK"

2. **Configure Pipeline**
   - In the Pipeline section:
     - Definition: "Pipeline script from SCM"
     - SCM: Git
     - Repository URL: [your-git-repository]
     - Script Path: `Jenkinsfile`

3. **Or Use Pipeline Script Directly**
   - Copy the content from `Jenkinsfile` in this repository
   - Paste it in the Pipeline script section

## 🏃 Running the Pipeline

### Build the Project

```bash
# Inside the container or with Maven installed locally
mvn clean install
```

### Run Jenkins Pipeline

1. Go to your pipeline job in Jenkins
2. Click "Build Now"
3. Watch the pipeline execute through all stages
4. Check the SonarQube analysis results

## 📊 Viewing Results

### In SonarQube

1. Go to http://localhost:9000
2. Click on your project
3. View:
   - Code Quality metrics
   - Code Coverage
   - Security vulnerabilities
   - Code Smells
   - Technical Debt

### In Jenkins

1. Build history with status
2. Test results
3. Build artifacts
4. SonarQube analysis link

## 🔍 Project Structure

```
sonar-jenkins/
├── docker-compose.yml           # Docker Compose configuration
├── Jenkinsfile                  # Jenkins pipeline definition
├── pom.xml                      # Maven project configuration
├── sonar-project.properties     # SonarQube project configuration
├── setup.sh                     # Setup script
├── src/
│   ├── main/java/com/example/
│   │   ├── Calculator.java      # Sample application code
│   │   └── StringUtil.java      # Utility class
│   └── test/java/com/example/
│       ├── CalculatorTest.java  # Unit tests
│       └── StringUtilTest.java  # Utility tests
└── data/                        # Docker volumes (auto-created)
    ├── jenkins/
    ├── sonarqube/
    └── postgres/
```

## 🛠️ Useful Commands

### Docker Commands

```bash
# View logs
docker-compose logs -f

# View specific service logs
docker-compose logs -f jenkins
docker-compose logs -f sonarqube

# Restart services
docker-compose restart

# Stop services
docker-compose down

# Stop and remove volumes
docker-compose down -v

# Check service status
docker-compose ps
```

### Maven Commands

```bash
# Clean and build
mvn clean install

# Run tests only
mvn test

# Run SonarQube analysis locally
mvn sonar:sonar \
  -Dsonar.projectKey=my-java-project \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=YOUR_TOKEN

# Generate coverage report
mvn clean test jacoco:report
```

### Jenkins CLI (Optional)

```bash
# Get Jenkins CLI jar
wget http://localhost:8080/jnlpJars/jenkins-cli.jar

# Build a job
java -jar jenkins-cli.jar -s http://localhost:8080/ build SonarQube-Demo
```

## 🐛 Troubleshooting

### SonarQube won't start

```bash
# Check logs
docker-compose logs sonarqube

# Increase virtual memory (Linux/Mac)
sudo sysctl -w vm.max_map_count=262144

# Make it permanent
echo "vm.max_map_count=262144" | sudo tee -a /etc/sysctl.conf
```

### Jenkins can't connect to SonarQube

- Verify both containers are on the same network
- Check the SonarQube URL in Jenkins: should be `http://sonarqube:9000`
- Verify the token is correct

### Permission Issues

```bash
# Fix permissions on data directories
sudo chmod -R 777 data/
```

### Port Already in Use

```bash
# Change ports in docker-compose.yml
# For example, change Jenkins port from 8080:8080 to 8081:8080
```

## 📚 Additional Resources

- [SonarQube Documentation](https://docs.sonarqube.org/)
- [Jenkins Documentation](https://www.jenkins.io/doc/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [JaCoCo Plugin](https://www.jacoco.org/jacoco/trunk/doc/)

## 🔐 Security Best Practices

1. Change default passwords immediately
2. Use secret management for tokens
3. Enable SSL/TLS in production
4. Regularly update Docker images
5. Use authentication and authorization
6. Scan for vulnerabilities regularly

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.
