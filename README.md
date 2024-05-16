# Library Management System

## Project Description
A Library Management System API using Spring Boot that allows librarians to manage books, patrons, and borrowing records.

## Requirements
- Java 11+
- Maven
- PostgreSQL

## Setup Instructions

### Database Setup
1. Install PostgreSQL.
2. Create a database named `library_db`.
3. Update `src/main/resources/application.properties` with your PostgreSQL username and password.

**application.properties**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/library_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
