# Bank System Microservice Architecture

## 📖 Overview

A modern, scalable banking system built using microservices architecture with Spring Boot. This project demonstrates how traditional banking functionalities can be decomposed into independent, loosely-coupled services that communicate through REST APIs and message brokering.

## 🏗️ Architecture

This banking system follows a domain-driven design approach, decomposing banking capabilities into discrete microservices:

### Core Services Architecture

- **API Gateway**: Single entry point that routes requests to appropriate microservices (Spring Cloud Gateway)
- **Service Discovery**: Eureka server for service registration and discovery
- **Account Service**: Manages customer accounts and balances (Spring Boot)
- **Transaction Service**: Handles fund transfers and transaction processing
- **Customer Service**: Manages customer information and KYC data
- **Notification Service**: Sends alerts and communications to users
- **Reporting Service**: Generates financial reports and statements

### Data Management

- **Development**: SQLite databases for each microservice for easy development
- **Production**: Neon PostgreSQL serverless databases for each microservice
- **Data Consistency**: Eventual consistency through event-driven architecture

### Communication Flow

```
Request → API Gateway → Service Discovery → Microservice → Database → Response
```

## 🛠️ Technologies Used

| Category | Technologies |
|----------|--------------|
| **Framework** | Spring Boot 3.x, Spring Cloud |
| **Service Discovery** | Netflix Eureka |
| **API Gateway** | Spring Cloud Gateway |
| **Database (Dev)** | SQLite |
| **Database (Prod)** | Neon PostgreSQL |
| **Testing** | JUnit 5, Mockito, Testcontainers |
| **API Documentation** | Springdoc OpenAPI |
| **Build Tool** | Maven or Gradle |
| **Containerization** | Docker, Docker Compose |
| **Monitoring** | Spring Boot Actuator, Micrometer |

## 🚀 Getting Started

### Prerequisites

- Java 17 or later
- Maven 3.6+ or Gradle 7+
- Docker and Docker Compose (for containerized deployment)
- SQLite (for development)

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/mohammed28s/Bank-System-Microservice-.git
   cd Bank-System-Microservice-
   ```

2. **Build the project**:
   ```bash
   # Using Maven
   mvn clean install
   
   # Using Gradle
   ./gradlew build
   ```

3. **Set up databases**:
   ```bash
   # For development, SQLite databases will be created automatically
   # For production, set up Neon PostgreSQL databases and update application-prod.properties files
   ```

### Configuration

Create `application-dev.properties` for development (SQLite):
```properties
spring.datasource.url=jdbc:sqlite:account-service.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.jpa.database-platform=com.example.config.SQLiteDialect
```

Create `application-prod.properties` for production (Neon PostgreSQL):
```properties
spring.datasource.url=jdbc:postgresql://neon-hostname:5432/account_service
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

### Running the System

1. **Start the service discovery first**:
   ```bash
   cd service-discovery
   mvn spring-boot:run
   ```

2. **Start the API Gateway**:
   ```bash
   cd api-gateway
   mvn spring-boot:run
   ```

3. **Start individual services** (in separate terminals):
   ```bash
   cd account-service
   mvn spring-boot:run -Dspring.profiles.active=dev
   
   cd transaction-service  
   mvn spring-boot:run -Dspring.profiles.active=dev
   
   cd customer-service
   mvn spring-boot:run -Dspring.profiles.active=dev
   ```

4. **Using Docker Compose**:
   ```bash
   docker-compose up --build
   ```

## 📚 API Documentation

Once services are running, access the following endpoints:

- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **Account Service Swagger**: http://localhost:8081/swagger-ui.html
- **Transaction Service Swagger**: http://localhost:8082/swagger-ui.html
- **Actuator Health Check**: http://localhost:8080/actuator/health

### Sample API Calls

**Create Account**:
```bash
curl -X POST "http://localhost:8080/api/accounts" \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "123e4567-e89b-12d3-a456-426614174000",
    "initialBalance": 1000.00,
    "accountType": "SAVINGS"
  }'
```

**Process Transaction**:
```bash
curl -X POST "http://localhost:8080/api/transactions" \
  -H "Content-Type: application/json" \
  -d '{
    "fromAccount": "1001",
    "toAccount": "1002", 
    "amount": 150.00,
    "description": "Fund transfer"
  }'
```

## 🗄️ Database Setup

### Development (SQLite)
SQLite databases are automatically created in each service directory with names like:
- `account-service.db`
- `transaction-service.db`
- `customer-service.db`

### Production (Neon PostgreSQL)
1. Create databases in Neon PostgreSQL for each service
2. Update the `application-prod.properties` files with connection details
3. Run migrations using Flyway or Liquibase

Example Neon PostgreSQL connection string:
```
jdbc:postgresql://ep-snowy-dream-123456.us-east-2.aws.neon.tech/account_service?sslmode=require
```

## 🚢 Deployment

### Docker Deployment

Each service has a Dockerfile:
```dockerfile
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

Build and run with Docker Compose:
```bash
docker-compose up --build
```

### Kubernetes Deployment (Optional)

Deploy to Kubernetes using the provided manifests:
```bash
kubectl apply -f k8s/
```

### Neon PostgreSQL Setup

1. Create a Neon account and project
2. Create databases for each microservice
3. Set up connection pooling for better performance
4. Configure automatic scaling based on workload

## 🔧 Configuration Management

Use Spring Cloud Config for centralized configuration:
```yaml
spring:
  cloud:
    config:
      uri: http://localhost:8888
  profiles:
    active: dev
```

## 🧪 Testing

Run tests for all services:
```bash
# Unit tests
mvn test

# Integration tests with Testcontainers
mvn verify -Dspring.profiles.active=test
```

## 🔒 Security

- JWT-based authentication with Spring Security
- Role-based access control
- Password encryption with BCrypt
- SSL/TLS for secure communication

## 📊 Monitoring

- Spring Boot Actuator for health checks and metrics
- Micrometer for application metrics
- Distributed tracing with Spring Cloud Sleuth
- Log aggregation with ELK stack or similar

## 🤝 Contributing

We welcome contributions to this project! Please follow these steps:

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a pull request

Please ensure your code follows the existing style and includes appropriate tests.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Thanks to the Spring Boot community for excellent documentation and tools
- Inspired by microservices patterns in modern banking systems
- Neon PostgreSQL for providing serverless Postgres capabilities