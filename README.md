# Person Manager - Spring Boot Application

A demo Spring Boot application for managing person records. Built with Spring Boot 2.7.3, featuring REST APIs, JPA persistence, and H2 in-memory database.

## Project Overview

This is a Maven-based Spring Boot project that provides a simple REST API for managing person data. It demonstrates core Spring Framework concepts including dependency injection, REST controllers, JPA data access, and testing.

## Technology Stack

- **Framework**: Spring Boot 2.7.3
- **Language**: Java 11
- **Build Tool**: Maven
- **Database**: H2 (in-memory)
- **ORM**: Spring Data JPA
- **Additional Libraries**: Lombok (for reducing boilerplate)
- **Testing**: Spring Boot Test, WebFlux (for testing)

## Project Structure

```
person-spring-openrewrite/
├── src/
│   ├── main/
│   │   ├── java/              # Java source code
│   │   │   └── com/javathinked/application/spring/
│   │   │       ├── controller/ # REST API endpoints
│   │   │       ├── model/      # Entity models
│   │   │       ├── repository/ # Data access layer
│   │   │       ├── service/    # Business logic
│   │   │       └── Application class
│   │   └── resources/
│   │       └── application.properties # Configuration
│   └── test/
│       └── java/              # Test source code
├── pom.xml                    # Maven dependencies and build configuration
├── mvnw / mvnw.cmd           # Maven wrapper (Windows/Unix)
├── .gitignore                # Git ignore file
└── README.md                 # This file
```

## Prerequisites

- **Java 11** or higher
- **Maven 3.6+** (or use the included Maven wrapper)
- **Git**

## Installation & Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/HattoriHenzo/person-spring-openrewrite.git
   cd person-spring-openrewrite
   ```

2. **Build the project**:
   ```bash
   # Using Maven wrapper (recommended)
   ./mvnw clean install
   
   # Or using system Maven
   mvn clean install
   ```

## Running the Application

### Option 1: Using Maven
```bash
./mvnw spring-boot:run
```

### Option 2: Running the JAR
```bash
./mvnw clean package
java -jar target/application-spring-0.0.1-SNAPSHOT.jar
```

### Option 3: From IDE
1. Open the project in your IDE (IntelliJ IDEA, Eclipse, etc.)
2. Find the main application class in `src/main/java`
3. Run it directly from the IDE

The application will start on **http://localhost:8080** by default.

## Available Endpoints

The application provides REST API endpoints for person management. Use Postman or curl to test the APIs:

```bash
# Example: Get all persons
curl http://localhost:8080/api/persons

# Example: Create a person
curl -X POST http://localhost:8080/api/persons \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com"}'
```

Refer to the Postman collection in the `postman/` directory for complete API examples.

## Configuration

The application uses `src/main/resources/application.properties` for configuration:

```properties
# Server configuration
server.port=8080

# H2 Database configuration
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

To access the H2 console, visit: **http://localhost:8080/h2-console**

## Running Tests

Execute the unit and integration tests:

```bash
# Using Maven
./mvnw test

# Or run specific test class
./mvnw test -Dtest=YourTestClass
```

## Development Notes

- **Lombok**: Used to reduce boilerplate (getters, setters, constructors). Ensure your IDE has the Lombok plugin installed.
- **H2 Database**: In-memory database is automatically created on startup. No external database setup required.
- **Maven Wrapper**: Use `./mvnw` (Unix) or `mvnw.cmd` (Windows) instead of system Maven for consistency.

## Troubleshooting

**Port already in use**:
```bash
java -jar target/application-spring-0.0.1-SNAPSHOT.jar --server.port=8081
```

**Maven not found**:
Ensure Java is installed and accessible:
```bash
java -version
```

## License

This is a demo/educational project.

## Contact

For questions or issues, refer to the repository or check the project documentation.
