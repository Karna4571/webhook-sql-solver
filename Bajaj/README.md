# Webhook SQL Solver - Spring Boot Application

A Spring Boot application that automatically processes webhook requests, solves SQL problems based on registration numbers, and submits solutions via webhook endpoints.

## Features

- **Automatic Webhook Generation**: Sends POST request to generate webhook on application startup
- **SQL Problem Solving**: Determines and solves SQL problems based on registration number
- **Database Storage**: Stores SQL problem results in H2 database
- **JWT Authentication**: Uses JWT tokens for secure API communication
- **No Manual Triggers**: Entire flow runs automatically on application startup

## Technology Stack

- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database**
- **RestTemplate** for HTTP requests
- **JWT** for authentication
- **Maven** for dependency management

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Quick Start

1. **Build the project**
   ```bash
   mvn clean package
   ```

2. **Run the application**
   ```bash
   java -jar target/webhook-sql-solver-1.0.0.jar
   ```

   Or using Maven:
   ```bash
   mvn spring-boot:run
   ```

## How It Works

1. **Application Startup**: The application automatically triggers the webhook flow when it starts up
2. **Webhook Generation**: Sends a POST request to generate webhook with registration details
3. **Problem Determination**: Based on the last two digits of the registration number:
   - **Odd numbers**: Question 1 - Find the second highest salary
   - **Even numbers**: Question 2 - Find the department with the highest average salary
4. **Solution Generation**: Generates the appropriate SQL query
5. **Database Storage**: Stores the problem and solution in the H2 database
6. **Solution Submission**: Sends the final SQL query to the webhook URL with JWT authentication

## SQL Solutions

### Question 1 (Odd registration numbers)
```sql
SELECT MAX(salary) FROM employees WHERE salary < (SELECT MAX(salary) FROM employees)
```

### Question 2 (Even registration numbers)
```sql
SELECT department_id, AVG(salary) as avg_salary FROM employees GROUP BY department_id ORDER BY avg_salary DESC LIMIT 1
```

## Project Structure

```
src/main/java/com/bajaj/
├── WebhookSqlSolverApplication.java    # Main application class
├── config/
│   └── RestTemplateConfig.java         # RestTemplate configuration
├── dto/
│   ├── WebhookRequest.java             # Webhook request DTO
│   ├── WebhookResponse.java            # Webhook response DTO
│   └── SolutionRequest.java            # Solution submission DTO
├── entity/
│   └── SqlResult.java                  # JPA entity for storing results
├── repository/
│   └── SqlResultRepository.java        # JPA repository interface
└── service/
    ├── WebhookService.java             # Main service orchestrating the flow
    └── SqlProblemSolver.java           # SQL problem solving logic
```

## Configuration

- **Database**: H2 In-Memory Database
- **Console**: Available at `http://localhost:8080/h2-console`
- **Credentials**: Username: `sa`, Password: `password`
- **JDBC URL**: `jdbc:h2:mem:testdb`

## API Endpoints

The application doesn't expose any REST endpoints as per requirements. The entire flow is triggered automatically on startup.

## Requirements Compliance

✅ **RestTemplate/WebClient**: Uses RestTemplate for HTTP requests  
✅ **No controller endpoints**: Flow is triggered automatically on startup  
✅ **JWT in Authorization header**: Properly implemented for solution submission  
✅ **Database storage**: H2 database with JPA for storing results  
✅ **Error handling**: Comprehensive error handling throughout the application  
✅ **Logging**: Detailed logging for debugging and monitoring  

## Notes

- The application uses the registration number "REG12347" as specified in the requirements
- Since the last two digits are "47" (odd), it will solve Question 1
- The application automatically handles JWT token authentication
- All operations are logged for debugging purposes 