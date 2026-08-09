# Money Manager App

A robust Full-Stack Java application designed to track, analyze, and manage personal finances efficiently. 

## 🛠️ Tech Stack
- **Backend:** Java, Spring Boot, Spring Security, Spring Data JPA
- **Database:** MySQL / PostgreSQL (Update based on your database)
- **Build Tool:** Maven

## 🚀 Current Implementation
The application currently features a solid foundational backend architecture setup, including:
- **Project Structure:** Fully initialized layered architecture (Controllers, Services, Repositories, Models).
- **User Registration:** Completed user signup endpoint with data validation.
- **Mail Service:** Integrated asynchronous email notification handling (e.g., for registration confirmations, account activation, or alerts).

### Mail Server (SMTP) Configuration
```properties
spring.mail.host=://gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
   ./mvnw spring-boot:run
   ```
