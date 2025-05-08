## Getting Started

This project is a Spring Boot application that uses a PostgreSQL database.

### Prerequisites

- Java 17+ installed
- Maven 3.8+ installed
- PostgreSQL installed and running

---

## Create the following property in app.properties file
jwt.secret=<JWT SECRETE PHRASE>
jwt.expiration=<JWT EXPIRATION NUMBER>

## Running app

1. **Create a PostgreSQL database:**
   CREATE DATABASE task_management; in your local database server
2. **Build the project:**
   ./mvnw clean package
3. **Run  the app **
   java -jar target/your-app-name-0.0.1-SNAPSHOT.jar




```

2. **Create a PostgreSQL database:**


