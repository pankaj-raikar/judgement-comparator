<div align="center">

<img src="assets/logo.png" alt="logo" width="120" />

# Judgment Comparator

**AI-Powered Court Judgment Comparator & Legal Assistant**

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.1-brightgreen.svg?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-25-blue.svg?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)

![Spring Security](https://img.shields.io/badge/Spring_Security-6.0-green?style=for-the-badge&logo=spring-security&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-0.12.6-orange?style=for-the-badge&logo=json-web-tokens&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2_Database-Engine-lightgrey?style=for-the-badge&logo=h2&logoColor=black)
![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

<br/>

[Features](#features) • [Installation](#installation) • [Usage](#usage) • [API Documentation](#api-documentation) • [Architecture](#architecture) • [Contributing](#contributing)

</div>

---

## 📖 Overview

**Judgment Comparator** is a sophisticated legal technology application built with **Spring Boot** and **Java 25**, featuring **JWT-based authentication** and **AI-powered analysis** for comparing court judgments. It leverages advanced text similarity algorithms, including the Jaccard Index, to provide comprehensive legal insights and help legal professionals identify patterns, precedents, and relationships between different court decisions.

## <a id="features"></a>✨ Features

### 🧠 AI-Powered Analysis

- **Smart Text Similarity**: Uses Jaccard Index algorithm for accurate text comparison.
- **Keyword Extraction**: Automatically identifies and matches common legal keywords.
- **Verdict Analysis**: Compares verdict types (allowed, dismissed, rejected).
- **Temporal Analysis**: Calculates year differences and historical context.
- **Intelligent Conclusions**: Generates AI-like analysis summaries.

### 🔐 JWT Authentication & Security

- **JWT Token-Based Authentication**: Secure token-based authentication system.
- **Role-Based Access Control**: Supports LAWYER, JUDGE, and ADMIN roles.
- **Spring Security Integration**: Comprehensive authentication and authorization.
- **Custom User Details Service**: Enhanced user management with custom implementations.
- **Password Encoding**: BCrypt password hashing for secure storage.
- **JWT Filter Integration**: Automatic token validation and security filtering.

### 🚀 Core Capabilities

- **Judgment Upload**: Easy-to-use API for storing court judgments.
- **Advanced Search**: Search judgments by keywords and case details.
- **Comparison Engine**: Core feature for comparing two judgments.
- **RESTful API**: Clean, documented API endpoints with JWT protection.
- **Database Integration**: H2 in-memory database with JPA repositories.

## <a id="installation"></a>🛠️ Installation & Setup

<details>
<summary><b>1. Prerequisites</b></summary>

Ensure you have the following installed:

- **Java 25** or higher
- **Maven 3.9+**
- **Git**

```bash
# Check Java version
java -version

# Check Maven version
mvn -version
```

</details>

<details>
<summary><b>2. Clone Repository</b></summary>

```bash
# Clone the repository
git clone https://github.com/pankaj-raikar/judgement-comparator.git
cd judgement-comparator

# List project contents
ls -la
```

</details>

<details>
<summary><b>3. JWT Configuration</b></summary>

Update `application.properties` with your JWT settings:

```properties
# JWT Configuration
jwt.secret=your-super-secret-jwt-key-here-make-it-long-and-complex
jwt.expiration=86400000
```

**Important**: Use a strong, unique secret key for production!

</details>

<details>
<summary><b>4. Build & Run</b></summary>

```bash
# Build the project
mvn clean package

# Run the application
mvn spring-boot:run

# Or run the JAR file
java -jar target/judgment-comparator-0.0.1-SNAPSHOT.jar
```

</details>

## <a id="usage"></a>🚀 Usage Guide

### Quick Start

The application starts on **http://localhost:8080** by default.

<details>
<summary><b>1. Register a User</b></summary>

Create your first user account:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "legal_professional",
    "email": "lawyer@legal.com",
    "password": "securepass123",
    "role": "LAWYER"
  }'
```

**Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "userId": 1,
  "username": "legal_professional",
  "email": "lawyer@legal.com",
  "role": "LAWYER"
}
```

</details>

<details>
<summary><b>2. Login User</b></summary>

Authenticate and receive JWT token:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "legal_professional",
    "password": "securepass123"
  }'
```

**Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "userId": 1,
  "username": "legal_professional",
  "email": "lawyer@legal.com",
  "role": "LAWYER"
}
```

</details>

<details>
<summary><b>3. Upload Judgments (JWT Required)</b></summary>

Add court judgments to the system (requires JWT token):

```bash
# First, save the token from login response
export JWT_TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

# Upload first judgment
curl -X POST http://localhost:8080/api/judgments \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $JWT_TOKEN" \
  -d '{
    "caseName": "Maneka Gandhi vs Union of India",
    "court": "Supreme Court",
    "year": 1978,
    "judgmentText": "The right to life under Article 21 includes the right to live with human dignity and is not limited to mere animal existence.",
    "keywords": "Article 21, Right to Life, Human Dignity",
    "verdict": "Petition allowed"
  }'

# Upload second judgment
curl -X POST http://localhost:8080/api/judgments \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $JWT_TOKEN" \
  -d '{
    "caseName": "K.S. Puttaswamy vs Union of India",
    "court": "Supreme Court",
    "year": 2017,
    "judgmentText": "The right to privacy is protected as an intrinsic part of the right to life and personal liberty under Article 21.",
    "keywords": "Article 21, Right to Privacy, Personal Liberty",
    "verdict": "Petition allowed"
  }'
```

</details>

<details>
<summary><b>4. Compare Judgments ⭐ (JWT Required)</b></summary>

Use the core comparison feature (requires JWT token):

```bash
# Compare the two judgments
curl -X POST "http://localhost:8080/api/judgments/compare?id1=1&id2=2" \
  -H "Authorization: Bearer $JWT_TOKEN"
```

**Expected Response:**

```json
{
  "judgment1": {
    "id": 1,
    "caseName": "Maneka Gandhi vs Union of India",
    "court": "Supreme Court",
    "year": 1978,
    "verdict": "Petition allowed"
  },
  "judgment2": {
    "id": 2,
    "caseName": "K.S. Puttaswamy vs Union of India",
    "court": "Supreme Court",
    "year": 2017,
    "verdict": "Petition allowed"
  },
  "analysis": {
    "similarityPercentage": 52.17,
    "commonKeywords": ["article 21"],
    "sameVerdictType": true,
    "yearDifference": 39
  },
  "conclusion": "These judgments show MODERATE SIMILARITY (52.17% match). They may share some common legal concepts but differ in application. Both were decided by the same court (Supreme Court)."
}
```

</details>

<details>
<summary><b>5. Get Current User Info</b></summary>

Retrieve current user information:

```bash
curl -X GET http://localhost:8080/api/auth/me \
  -H "Authorization: Bearer $JWT_TOKEN"
```

</details>

## <a id="api-documentation"></a>📚 API Documentation

### Authentication Endpoints

| Method | Endpoint             | Description       | Auth Required |
| ------ | -------------------- | ----------------- | ------------- |
| `POST` | `/api/auth/register` | Register new user | ❌ No         |
| `POST` | `/api/auth/login`    | User login        | ❌ No         |
| `GET`  | `/api/auth/me`       | Get current user  | ✅ Yes        |

### Judgment Endpoints

| Method | Endpoint                 | Description           | Auth Required |
| ------ | ------------------------ | --------------------- | ------------- |
| `POST` | `/api/judgments`         | Upload judgment       | ✅ Yes        |
| `GET`  | `/api/judgments`         | Get all judgments     | ✅ Yes        |
| `POST` | `/api/judgments/compare` | Compare two judgments | ✅ Yes        |
| `GET`  | `/`                      | Welcome page          | ❌ No         |

### Request/Response Examples

<details>
<summary><b>User Registration</b></summary>

**Request:**

```json
{
  "username": "judge_smith",
  "email": "judge@courts.gov",
  "password": "securepassword",
  "role": "JUDGE"
}
```

**Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "userId": 1,
  "username": "judge_smith",
  "email": "judge@courts.gov",
  "role": "JUDGE"
}
```

</details>

<details>
<summary><b>User Login</b></summary>

**Request:**

```json
{
  "username": "judge_smith",
  "password": "securepassword"
}
```

**Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "userId": 1,
  "username": "judge_smith",
  "email": "judge@courts.gov",
  "role": "JUDGE"
}
```

</details>

<details>
<summary><b>JWT Token Usage</b></summary>

Include the JWT token in the Authorization header for protected endpoints:

```bash
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Note**: All judgment-related endpoints require a valid JWT token.

</details>

## <a id="architecture"></a>🏗️ Architecture

```mermaid
graph LR
    A[REST Controllers] --> B[Service Layer]
    B --> C[JPA Repositories]
    C --> D[H2 Database]
    B --> E[AI Comparison Engine]
    E --> F[Jaccard Similarity]
    E --> G[Keyword Analysis]
    E --> H[Verdict Comparison]

    I[Spring Security] --> A
    J[Validation] --> A

    K[JWT Service] --> L[JWT Filter]
    L --> A
    M[Auth Controller] --> K
    M --> N[User Service]

    style A fill:#2d3748,stroke:#63b3ed,stroke-width:2px
    style B fill:#4a5568,stroke:#9f7aea,stroke-width:2px
    style E fill:#553c9a,stroke:#ed8936,stroke-width:2px
    style D fill:#2f855a,stroke:#48bb78,stroke-width:2px
    style K fill:#742a2a,stroke:#fc8181,stroke-width:2px
    style M fill:#744210,stroke:#f6ad55,stroke-width:2px
```

## 📂 Project Structure

```
judgment-comparator/
├── src/main/java/com/legaltech/judgment_comparator/
│   ├── config/           # Security configuration
│   ├── controller/       # REST API controllers
│   │   ├── AuthController.java     # JWT Authentication
│   │   ├── JudgmentController.java # Judgment operations
│   │   └── UserController.java     # User management
│   ├── dto/              # Data Transfer Objects
│   │   ├── AuthResponse.java       # JWT response
│   │   ├── LoginRequest.java       # Login request
│   │   └── RegisterRequest.java    # Registration request
│   ├── entity/          # JPA entities (Judgment, User)
│   ├── exception/       # Global exception handling
│   ├── repository/      # Data access repositories
│   ├── security/        # JWT Security Components
│   │   ├── JwtService.java         # JWT generation/validation
│   │   ├── JwtAuthenticationFilter.java # JWT filter
│   │   └── CustomUserDetailsService.java # User details service
│   ├── service/         # Business logic services
│   └── JudgmentComparatorApplication.java
├── src/main/resources/
│   └── application.properties
├── api-docs/            # API documentation
├── pom.xml             # Maven configuration with JWT dependencies
└── README.md           # This file
```

## 🧪 Testing

```bash
# Run unit tests
mvn test

# Run tests with coverage
mvn clean test

# Run specific test class
mvn test -Dtest=JudgmentComparatorApplicationTests
```

## 🚀 Deployment

<details>
<summary><b>Production Configuration</b></summary>

**application.properties for Production:**

```properties
# JWT Configuration
jwt.secret=your-production-jwt-secret-key-256-bits-minimum
jwt.expiration=86400000

# Database Configuration
spring.datasource.url=jdbc:h2:mem:prod
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Security Configuration
spring.security.require-ssl=true
```

</details>

<details>
<summary><b>Production Build</b></summary>

```bash
# Create production JAR
mvn clean package -Pprod

# Run with production profile
java -jar target/judgment-comparator-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

</details>

## 📊 Technology Stack

| Component                 | Technology         | Version |
| ------------------------- | ------------------ | ------- |
| **Backend Framework**     | Spring Boot        | 4.0.1   |
| **Language**              | Java               | 25      |
| **Authentication**        | JWT (JJWT)         | 0.12.6  |
| **Security**              | Spring Security    | 6.0     |
| **Database**              | H2 Database        | Engine  |
| **Build Tool**            | Apache Maven       | 3.9+    |
| **Validation**            | Jakarta Validation | -       |
| **ORM**                   | Spring Data JPA    | -       |
| **Logging**               | SLF4J              | -       |
| **Boilerplate Reduction** | Lombok             | -       |

## 🔒 Security Features

- **JWT Token Authentication**: Secure stateless authentication with JWT tokens.
- **Password Encoding**: BCrypt hashing for secure password storage.
- **Role-Based Access Control**: Supports LAWYER, JUDGE, and ADMIN roles.
- **Custom User Details Service**: Enhanced user management implementation.
- **JWT Filter Integration**: Automatic token validation and security filtering.
- **Input Validation**: Comprehensive request validation using Jakarta Validation.
- **SQL Injection Protection**: JPA/Hibernate ORM protection.
- **XSS Prevention**: Spring Security defaults.
- **CSRF Protection**: Built-in CSRF token validation.

## 🔑 JWT Configuration

### Required Properties

Add these to your `application.properties`:

```properties
# JWT Secret Key (minimum 256 bits recommended)
jwt.secret=your-super-secret-jwt-key-here-make-it-long-and-complex

# JWT Expiration Time (in milliseconds)
# 86400000 = 24 hours
jwt.expiration=86400000
```

### Security Best Practices

- **Strong Secret Key**: Use a cryptographically strong secret key (minimum 256 bits)
- **Token Expiration**: Set reasonable expiration times (24 hours recommended)
- **HTTPS Only**: Always use HTTPS in production
- **Regular Key Rotation**: Rotate JWT secret keys periodically

## 📈 Performance Considerations

- **In-Memory Database**: H2 for fast development and
