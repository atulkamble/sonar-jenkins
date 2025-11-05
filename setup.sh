#!/bin/bash

# SonarQube + Jenkins Setup Script
# This script helps you set up the complete environment

set -e

echo "========================================="
echo "SonarQube + Jenkins Setup"
echo "========================================="
echo ""

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Function to print colored output
print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

# Check if Docker is installed
echo "Checking prerequisites..."
if ! command -v docker &> /dev/null; then
    print_error "Docker is not installed. Please install Docker first."
    exit 1
fi
print_success "Docker is installed"

if ! command -v docker-compose &> /dev/null; then
    print_error "Docker Compose is not installed. Please install Docker Compose first."
    exit 1
fi
print_success "Docker Compose is installed"

# Create data directories
echo ""
echo "Creating data directories..."
mkdir -p data/{jenkins,postgres}
mkdir -p data/sonarqube/{conf,data,extensions,logs}
print_success "Data directories created"

# Set proper permissions
echo ""
echo "Setting permissions..."
chmod -R 777 data/
print_success "Permissions set"

# Start services
echo ""
echo "Starting services..."
docker-compose up -d

echo ""
print_success "All services are starting up!"
echo ""
echo "========================================="
echo "Access Information:"
echo "========================================="
echo "Jenkins:    http://localhost:8081"
echo "SonarQube:  http://localhost:9000"
echo ""
echo "Default SonarQube credentials:"
echo "Username: admin"
echo "Password: admin (you'll be prompted to change this)"
echo ""
echo "========================================="
echo "Next Steps:"
echo "========================================="
echo "1. Wait for services to start (may take 1-2 minutes)"
echo "2. Access SonarQube at http://localhost:9000 and change the default password"
echo "3. Generate a SonarQube token for Jenkins integration"
echo "4. Access Jenkins at http://localhost:8081"
echo "5. Configure SonarQube scanner in Jenkins"
echo ""
echo "To view logs: docker-compose logs -f"
echo "To stop services: docker-compose down"
echo "========================================="
