# SonarQube Jenkins Integration - Troubleshooting Guide

## Issue Resolved: SonarQube Version Incompatibility

### Problem
The SonarQube container was failing to start with the error:
```
The version of SonarQube you are trying to upgrade from is too old. 
Please upgrade to the 24.12 version first.
```

### Root Cause
- Old SonarQube data in `./data/sonarqube/` directory was incompatible with the latest version
- The health check was timing out before SonarQube could fully initialize

### Solution Applied
1. **Removed old data**: Cleaned up incompatible database and configuration files
2. **Updated health check**: Increased intervals and added start_period for better reliability
   - `start_period: 120s` - Gives SonarQube 2 minutes to initialize before health checks start
   - `interval: 45s` - Check every 45 seconds
   - `timeout: 15s` - Allow up to 15 seconds per health check
   - `retries: 10` - Retry up to 10 times before marking as unhealthy

3. **Removed deprecated config**: Removed `version: '3.8'` from docker-compose.yml

## Services Status

All services are now running successfully:

- **PostgreSQL**: `localhost:5432` (internal only)
  - Status: ✅ Healthy
  - Database: sonarqube
  - User: sonar

- **SonarQube**: `http://localhost:9000`
  - Status: ✅ Healthy
  - Default credentials: admin / admin (change on first login)

- **Jenkins**: `http://localhost:8080`
  - Status: ✅ Running
  - Jenkins home: `./data/jenkins`

## Quick Commands

### Start all services
```bash
docker-compose up -d
```

### Stop all services
```bash
docker-compose down
```

### View logs
```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f sonarqube
docker-compose logs -f jenkins
docker-compose logs -f postgres
```

### Check service status
```bash
docker-compose ps
```

### Clean restart (removes all data)
```bash
docker-compose down -v
rm -rf data/sonarqube/* data/postgres/*
docker-compose up -d
```

## Next Steps

1. **Access SonarQube** at `http://localhost:9000`
   - Login with default credentials: `admin` / `admin`
   - You'll be prompted to change the password on first login

2. **Access Jenkins** at `http://localhost:8080`
   - Get the initial admin password:
     ```bash
     docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
     ```

3. **Configure SonarQube Scanner in Jenkins**
   - Install SonarQube Scanner plugin in Jenkins
   - Configure SonarQube server in Jenkins settings
   - Generate a token in SonarQube for Jenkins integration

4. **Run your first analysis**
   - Create a Jenkins pipeline job
   - Add SonarQube analysis step
   - Check results in SonarQube dashboard

## Common Issues

### SonarQube taking long to start
- Normal behavior on first start
- Can take 2-3 minutes
- Check logs: `docker logs sonarqube`

### Port conflicts
If ports 8080, 9000, or 5432 are already in use:
- Stop conflicting services
- Or modify ports in docker-compose.yml

### Insufficient resources
SonarQube requires:
- Minimum 2GB RAM
- 4GB recommended
- Check Docker Desktop resource settings

## References
- [SonarQube Documentation](https://docs.sonarqube.org/latest/)
- [Jenkins Documentation](https://www.jenkins.io/doc/)
- [SonarQube Scanner for Jenkins](https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-jenkins/)
