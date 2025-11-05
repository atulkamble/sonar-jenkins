# Quick Start Guide - SonarQube + Jenkins Integration

## 🎯 Your Services Are Running!

All three services are now up and running:

### Access URLs:
- **SonarQube**: http://localhost:9000
- **Jenkins**: http://localhost:8081
- **PostgreSQL**: localhost:5432 (internal use)

---

## 📊 SonarQube Setup (Step 1)

1. **Open SonarQube**: http://localhost:9000
2. **Login**:
   - Username: `admin`
   - Password: `admin`
3. **Change Password**: You'll be prompted to change it on first login
4. **Create a Token**:
   - Go to: User → My Account → Security → Generate Tokens
   - Name: `jenkins-integration`
   - Type: `Global Analysis Token`
   - **Save this token** - you'll need it for Jenkins!

---

## 🔧 Jenkins Setup (Step 2)

Since Jenkins was configured to skip the setup wizard, you need to:

### Option 1: Reconfigure to Enable Setup Wizard

1. Stop the containers:
   ```bash
   docker-compose down
   ```

2. Edit `docker-compose.yml` and remove this line from the Jenkins service:
   ```yaml
   JAVA_OPTS: "-Djenkins.install.runSetupWizard=false"
   ```

3. Restart:
   ```bash
   docker-compose up -d
   ```

4. Get the initial admin password:
   ```bash
   docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
   ```

5. Open Jenkins at http://localhost:8081 and follow the setup wizard

### Option 2: Quick Configuration

1. Access Jenkins directly at http://localhost:8081
2. You can start using it without authentication (not recommended for production!)

---

## 🚀 Test the Java Project

Once you have both SonarQube and Jenkins set up:

### Build the Maven Project:

```bash
cd /Users/atul/Downloads/sonar-jenkins
mvn clean install
```

### Run Tests:

```bash
mvn test
```

### Run SonarQube Analysis Locally:

```bash
mvn sonar:sonar \
  -Dsonar.projectKey=my-java-project \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=YOUR_SONARQUBE_TOKEN
```

Replace `YOUR_SONARQUBE_TOKEN` with the token you generated in SonarQube.

---

## 📁 Project Structure

```
sonar-jenkins/
├── docker-compose.yml           # Docker services configuration
├── Jenkinsfile                  # CI/CD pipeline definition
├── pom.xml                      # Maven project configuration
├── sonar-project.properties     # SonarQube configuration
├── src/
│   ├── main/java/com/example/
│   │   ├── Calculator.java      # Sample calculator class
│   │   └── StringUtil.java      # String utility class
│   └── test/java/com/example/
│       ├── CalculatorTest.java  # Calculator unit tests
│       └── StringUtilTest.java  # StringUtil unit tests
├── SETUP_GUIDE.md              # Detailed setup guide
└── QUICK_START.md              # This file
```

---

## 🛠️ Useful Commands

### Docker Management

```bash
# View logs
docker-compose logs -f

# View specific service logs
docker-compose logs -f jenkins
docker-compose logs -f sonarqube

# Check container status
docker-compose ps

# Restart services
docker-compose restart

# Stop services
docker-compose down

# Stop and remove all data
docker-compose down -v
```

### Maven Commands

```bash
# Clean and build
mvn clean install

# Run tests only
mvn test

# Run with code coverage
mvn clean test jacoco:report

# Skip tests during build
mvn clean install -DskipTests
```

---

## 🔍 Next Steps

1. ✅ **SonarQube is accessible** at http://localhost:9000
2. ✅ **Jenkins is accessible** at http://localhost:8081
3. ✅ **Sample Java project** with tests is ready to build
4. **Configure Jenkins** to connect to SonarQube (see SETUP_GUIDE.md)
5. **Create a Jenkins pipeline job** using the provided Jenkinsfile
6. **Run the pipeline** to see the full CI/CD flow in action!

---

## 📖 For Detailed Configuration

See `SETUP_GUIDE.md` for:
- Detailed Jenkins configuration steps
- Installing required Jenkins plugins
- Creating Jenkins pipeline jobs
- Configuring SonarQube quality gates
- Troubleshooting common issues

---

## ✨ What You Get

This complete setup provides:
- ✅ **Code Quality Analysis** with SonarQube
- ✅ **Continuous Integration** with Jenkins
- ✅ **Unit Testing** with JUnit 5
- ✅ **Code Coverage** with JaCoCo
- ✅ **Sample Java Application** ready to analyze
- ✅ **Pipeline as Code** with Jenkinsfile
- ✅ **Docker-based Setup** for easy deployment

---

**Happy Coding! 🚀**
