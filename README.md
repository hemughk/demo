# User Forms Spring Boot Project

This project contains a minimal Spring Boot (Java 8, Maven) application that maps a multi-step form (participant + loan & security details) to JPA entities and exposes REST CRUD endpoints.

How to run:
1. Create the database: mysql -u root -p < docs/create_database.sql
2. Edit src/main/resources/application.properties and set your DB credentials.
3. Build and run:
   mvn clean package
   java -jar target/user-forms-0.0.1-SNAPSHOT.jar

API highlights:
- POST /api/participants
- POST /api/loan-records (nested JSON)
- POST /api/loan-records/{loanId}/files (multipart files)

