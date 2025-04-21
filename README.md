# Core Lending Loan Origination Microservice

## Overview

The Core Lending Loan Origination Microservice is a critical component of the Firefly platform, responsible for managing the loan origination process. This service handles loan applications, underwriting decisions, scoring, and other aspects of the loan origination workflow.

Built with Java 21 and Spring Boot, this microservice follows a reactive programming model using Project Reactor (Mono/Flux) for efficient handling of asynchronous operations and high throughput.

## Architecture

The microservice is structured as a multi-module Maven project with the following components:

### Modules

1. **core-lending-loan-origination-interfaces**
   - Contains DTOs (Data Transfer Objects), interfaces, and enums
   - Defines the contract between the microservice and its clients
   - Includes validation annotations and Swagger/OpenAPI documentation

2. **core-lending-loan-origination-models**
   - Contains entity definitions and repository interfaces
   - Manages database interactions and data persistence
   - Implements JPA repositories for data access

3. **core-lending-loan-origination-core**
   - Contains business logic and service implementations
   - Implements the core functionality of the microservice
   - Handles transaction management and business rules

4. **core-lending-loan-origination-web**
   - Contains REST controllers and API endpoints
   - Manages HTTP requests and responses
   - Implements API versioning and documentation

## Key Features

- **Loan Application Management**: Create, retrieve, update, and delete loan applications
- **Underwriting Decision Management**: Record and manage underwriting decisions for loan applications
- **Scoring System**: Evaluate and score loan applications based on various criteria
- **Reactive Programming Model**: Non-blocking I/O for high throughput and scalability
- **RESTful API**: Well-documented API with OpenAPI/Swagger annotations
- **Pagination and Filtering**: Support for paginated results and advanced filtering

## Prerequisites

- Java Development Kit (JDK) 21
- Maven 3.8+
- Docker (for containerized deployment)
- PostgreSQL (or compatible database)

## Setup and Installation

### Local Development

1. Clone the repository:
   ```bash
   git clone https://github.com/your-organization/core-lending-loan-origination.git
   cd core-lending-loan-origination
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run -pl core-lending-loan-origination-web
   ```

### Docker Deployment

1. Build the Docker image:
   ```bash
   mvn clean package
   docker build -t core-lending-loan-origination:latest .
   ```

2. Run the Docker container:
   ```bash
   docker run -p 8080:8080 core-lending-loan-origination:latest
   ```

## Configuration

The application can be configured through the `application.properties` or `application.yml` file in the `core-lending-loan-origination-web/src/main/resources` directory. Key configuration properties include:

- Database connection settings
- Logging levels
- Security settings
- External service endpoints

## API Documentation

The API is documented using OpenAPI/Swagger. When the application is running, you can access the API documentation at:

```
http://localhost:8080/swagger-ui.html
```

### Key Endpoints

- **Loan Applications**
  - `GET /api/v1/loan-applications` - List all loan applications
  - `POST /api/v1/loan-applications` - Create a new loan application
  - `GET /api/v1/loan-applications/{applicationId}` - Get a specific loan application
  - `PUT /api/v1/loan-applications/{applicationId}` - Update a loan application
  - `DELETE /api/v1/loan-applications/{applicationId}` - Delete a loan application

- **Underwriting Scores**
  - `GET /api/v1/loan-applications/{applicationId}/scores` - List scores for an application
  - `POST /api/v1/loan-applications/{applicationId}/scores` - Create a new score
  - `GET /api/v1/loan-applications/{applicationId}/scores/{scoreId}` - Get a specific score
  - `PUT /api/v1/loan-applications/{applicationId}/scores/{scoreId}` - Update a score
  - `DELETE /api/v1/loan-applications/{applicationId}/scores/{scoreId}` - Delete a score

- **Underwriting Decisions**
  - `GET /api/v1/loan-applications/{applicationId}/decisions` - List decisions for an application
  - `POST /api/v1/loan-applications/{applicationId}/decisions` - Create a new decision
  - `GET /api/v1/loan-applications/{applicationId}/decisions/{decisionId}` - Get a specific decision
  - `PUT /api/v1/loan-applications/{applicationId}/decisions/{decisionId}` - Update a decision
  - `DELETE /api/v1/loan-applications/{applicationId}/decisions/{decisionId}` - Delete a decision

## Development Guidelines

### Code Style

This project follows the Google Java Style Guide. You can ensure your code adheres to the style guide by using the provided checkstyle configuration:

```bash
mvn checkstyle:check
```

### Testing

The project uses JUnit 5 for unit testing and Spring Boot Test for integration testing. To run the tests:

```bash
mvn test
```

For test coverage reports:

```bash
mvn jacoco:report
```

### Branching Strategy

- `main` - Production-ready code
- `develop` - Integration branch for feature development
- `feature/*` - Feature branches
- `bugfix/*` - Bug fix branches
- `release/*` - Release preparation branches

### Commit Message Format

Follow the conventional commits specification:

```
<type>(<scope>): <description>

[optional body]

[optional footer(s)]
```

Types: feat, fix, docs, style, refactor, test, chore

## Deployment

### CI/CD Pipeline

The project uses a CI/CD pipeline for automated testing, building, and deployment. The pipeline is triggered on push to the repository and includes:

1. Code quality checks
2. Unit and integration tests
3. Building the application
4. Building the Docker image
5. Deploying to the target environment

### Environments

- **Development**: Automatically deployed from the `develop` branch
- **Staging**: Deployed from `release/*` branches after QA approval
- **Production**: Deployed from the `main` branch after staging validation

## Monitoring and Logging

The application includes:

- Prometheus metrics for monitoring
- Structured logging with JSON format
- Distributed tracing with Spring Cloud Sleuth and Zipkin
- Health check endpoints for Kubernetes liveness and readiness probes

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'feat: add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request