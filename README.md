# 💰 Money Manager App

A **Full-Stack Java application** designed to track, analyze, and manage personal finances efficiently.

## 🛠️ Tech Stack

* **Backend:** Java 26, Spring Boot 4.1
* **Security:** Spring Security, BCrypt, JWT
* **Database:** MySQL
* **Persistence:** Spring Data JPA, Hibernate
* **Email:** Spring Mail, Brevo SMTP
* **Utilities:** Lombok
* **Mapping:** MapStruct
* **Build Tool:** Maven

## 🚀 Current Implementation

The backend currently includes:

* 👤 **User Registration & Profile Management**
* 🔐 **Spring Security Integration**
* 🔑 **Secure Password Hashing with BCrypt**
* 📧 **Email-Based Account Activation**
* 🎟️ **Activation Token Generation**
* 🗄️ **MySQL Database Integration**
* 🔄 **DTO & Entity Mapping with MapStruct**
* ✅ **Request Validation**
* 🌐 **CORS & Stateless Security Configuration**
* 🧪 **Spring Boot & Security Testing Support**
* 📊 **LOGIN BY USERNAME AND PASSWORD AFTER ACTIVATE PROFILE**
* 🔒 **JWT Dependencies for Authentication**

## 📧 Mail Configuration

Configure your SMTP credentials using environment variables:

```properties
spring.mail.host=smtp-relay.brevo.com
spring.mail.port=587
spring.mail.username=${BREVO_USERNAME}
spring.mail.password=${BREVO_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

🚧 **Currently under development**

The project is being developed step-by-step, with authentication, user management, security, and financial management features being added progressively.
