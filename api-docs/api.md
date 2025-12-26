# Complete API Endpoints Reference Guide

## 📍 Base URL

```
http://localhost:8080
```

---

# 1️⃣ Public Endpoints (No Authentication Required)

## 1.1 Health Check

### GET /

**Description:** Welcome endpoint to verify API is running

**cURL Command:**

```bash
curl http://localhost:8080/
```

**Expected Response:**

```json
{
  "application": "AI Powered Court Judgment Comparator",
  "version": "1.0.0",
  "status": "running",
  "timestamp": "2025-12-27T01:06:00.123456",
  "endpoints": {
    "judgments": "/api/judgments",
    "users": "/api/users",
    "h2Console": "/h2-console"
  }
}
```

**Status Code:** `200 OK`

---

## 1.2 Health Status

### GET /health

**Description:** Health check endpoint

**cURL Command:**

```bash
curl http://localhost:8080/health
```

**Expected Response:**

```json
{
  "status": "UP"
}
```

**Status Code:** `200 OK`

---

# 2️⃣ Authentication Endpoints

## 2.1 Register User

### POST /api/auth/register

**Description:** Register a new user account

**cURL Command:**

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "rahul_lawyer",
    "email": "rahul@legal.com",
    "password": "pass123",
    "role": "LAWYER"
  }'
```

**Request Body:**

```json
{
  "username": "rahul_lawyer",
  "email": "rahul@legal.com",
  "password": "pass123",
  "role": "LAWYER" // Options: LAWYER, STUDENT, ADMIN
}
```

**Expected Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWh1bF9sYXd5ZXIiLCJpYXQiOjE3MzUyNDgzNjAsImV4cCI6MTczNTMzNDc2MH0.7wK5xYz9vN8pQ2mR4tL6sH3jF1gD9bC0aE5wX8vY2zA",
  "type": "Bearer",
  "userId": 1,
  "username": "rahul_lawyer",
  "email": "rahul@legal.com",
  "role": "LAWYER"
}
```

**Status Code:** `201 Created`

**Error Response (Username exists):**

```json
{
  "timestamp": "2025-12-27T01:06:30.123456",
  "status": 400,
  "error": "Bad Request",
  "message": "Username already exists: rahul_lawyer"
}
```

**Status Code:** `400 Bad Request`

---

## 2.2 Login

### POST /api/auth/login

**Description:** Login with existing credentials

**cURL Command:**

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "rahul_lawyer",
    "password": "pass123"
  }'
```

**Request Body:**

```json
{
  "username": "rahul_lawyer",
  "password": "pass123"
}
```

**Expected Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWh1bF9sYXd5ZXIiLCJpYXQiOjE3MzUyNDg0MjAsImV4cCI6MTczNTMzNDgyMH0.9kL2xYz7vN6pQ4mR8tL3sH1jF9gD5bC2aE7wX6vY4zA",
  "type": "Bearer",
  "userId": 1,
  "username": "rahul_lawyer",
  "email": "rahul@legal.com",
  "role": "LAWYER"
}
```

**Status Code:** `200 OK`

**Error Response (Invalid credentials):**

```json
{
  "timestamp": "2025-12-27T01:07:00.123456",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid username or password"
}
```

**Status Code:** `400 Bad Request`

---

## 2.3 Get Current User

### GET /api/auth/me

**Description:** Get currently authenticated user information

**cURL Command:**

```bash
curl -X GET http://localhost:8080/api/auth/me \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Expected Response:**

```json
{
  "id": 1,
  "username": "rahul_lawyer",
  "email": "rahul@legal.com",
  "password": "***",
  "role": "LAWYER",
  "active": true
}
```

**Status Code:** `200 OK`

**Error Response (No token):**

```json
{
  "timestamp": "2025-12-27T01:07:30.123456",
  "status": 401,
  "error": "Unauthorized",
  "message": "Full authentication is required to access this resource"
}
```

**Status Code:** `401 Unauthorized`

---

# 3️⃣ Judgment Endpoints (🔒 Authentication Required)

## 3.1 Upload Judgment

### POST /api/judgments

**Description:** Upload a new court judgment

**cURL Command:**

```bash
curl -X POST http://localhost:8080/api/judgments \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "caseName": "Maneka Gandhi vs Union of India",
    "court": "Supreme Court",
    "year": 1978,
    "judgmentText": "The right to life under Article 21 includes the right to live with human dignity. Personal liberty cannot be restricted except through fair, just and reasonable procedure.",
    "keywords": "Article 21, Right to Life, Personal Liberty, Due Process",
    "verdict": "Petition allowed"
  }'
```

**Request Body:**

```json
{
  "caseName": "Maneka Gandhi vs Union of India",
  "court": "Supreme Court",
  "year": 1978,
  "judgmentText": "The right to life under Article 21 includes the right to live with human dignity. Personal liberty cannot be restricted except through fair, just and reasonable procedure.",
  "keywords": "Article 21, Right to Life, Personal Liberty, Due Process",
  "verdict": "Petition allowed"
}
```

**Expected Response:**

```json
{
  "id": 1,
  "caseName": "Maneka Gandhi vs Union of India",
  "court": "Supreme Court",
  "year": 1978,
  "judgmentText": "The right to life under Article 21 includes the right to live with human dignity. Personal liberty cannot be restricted except through fair, just and reasonable procedure.",
  "keywords": "Article 21, Right to Life, Personal Liberty, Due Process",
  "verdict": "Petition allowed",
  "uploadedAt": "2025-12-27T01:08:00.123456"
}
```

**Status Code:** `201 Created`

---

## 3.2 Get All Judgments

### GET /api/judgments

**Description:** Retrieve all judgments

**cURL Command:**

```bash
curl -X GET http://localhost:8080/api/judgments \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Expected Response:**

```json
[
  {
    "id": 1,
    "caseName": "Maneka Gandhi vs Union of India",
    "court": "Supreme Court",
    "year": 1978,
    "judgmentText": "The right to life under Article 21...",
    "keywords": "Article 21, Right to Life, Personal Liberty",
    "verdict": "Petition allowed",
    "uploadedAt": "2025-12-27T01:08:00.123456"
  },
  {
    "id": 2,
    "caseName": "K.S. Puttaswamy vs Union of India",
    "court": "Supreme Court",
    "year": 2017,
    "judgmentText": "The right to privacy is protected...",
    "keywords": "Article 21, Right to Privacy, Dignity",
    "verdict": "Petition allowed",
    "uploadedAt": "2025-12-27T01:09:00.123456"
  }
]
```

**Status Code:** `200 OK`

---

## 3.3 Get Judgment by ID

### GET /api/judgments/{id}

**Description:** Retrieve a specific judgment by ID

**cURL Command:**

```bash
curl -X GET http://localhost:8080/api/judgments/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Expected Response:**

```json
{
  "id": 1,
  "caseName": "Maneka Gandhi vs Union of India",
  "court": "Supreme Court",
  "year": 1978,
  "judgmentText": "The right to life under Article 21 includes the right to live with human dignity.",
  "keywords": "Article 21, Right to Life, Personal Liberty",
  "verdict": "Petition allowed",
  "uploadedAt": "2025-12-27T01:08:00.123456"
}
```

**Status Code:** `200 OK`

**Error Response (Not found):**

```json
{
  "timestamp": "2025-12-27T01:10:00.123456",
  "status": 400,
  "error": "Bad Request",
  "message": "Judgment not found with ID: 999"
}
```

**Status Code:** `400 Bad Request`

---

## 3.4 Search Judgments

### GET /api/judgments/search?term={searchTerm}

**Description:** Search judgments by keyword (case name, keywords, court)

**cURL Command:**

```bash
curl -X GET "http://localhost:8080/api/judgments/search?term=Article%2021" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Expected Response:**

```json
[
  {
    "id": 1,
    "caseName": "Maneka Gandhi vs Union of India",
    "court": "Supreme Court",
    "year": 1978,
    "judgmentText": "The right to life under Article 21...",
    "keywords": "Article 21, Right to Life",
    "verdict": "Petition allowed",
    "uploadedAt": "2025-12-27T01:08:00.123456"
  },
  {
    "id": 2,
    "caseName": "K.S. Puttaswamy vs Union of India",
    "court": "Supreme Court",
    "year": 2017,
    "judgmentText": "Article 21 protects privacy...",
    "keywords": "Article 21, Privacy",
    "verdict": "Petition allowed",
    "uploadedAt": "2025-12-27T01:09:00.123456"
  }
]
```

**Status Code:** `200 OK`

---

## 3.5 Compare Two Judgments ⭐ (Core Feature)

### POST /api/judgments/compare?id1={id1}&id2={id2}

**Description:** Compare two judgments and get similarity analysis

**cURL Command:**

```bash
curl -X POST "http://localhost:8080/api/judgments/compare?id1=1&id2=2" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
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
    "commonKeywords": ["article 21", "personal liberty"],
    "sameVerdictType": true,
    "yearDifference": 39
  },
  "conclusion": "These judgments show MODERATE SIMILARITY (52.17% match). They may share some common legal concepts but differ in application. Both were decided by the same court (Supreme Court)."
}
```

**Status Code:** `200 OK`

**Error Response (Same judgment comparison):**

```json
{
  "error": "Cannot compare a judgment with itself"
}
```

**Status Code:** `400 Bad Request`

---

## 3.6 Delete Judgment

### DELETE /api/judgments/{id}

**Description:** Delete a judgment by ID

**cURL Command:**

```bash
curl -X DELETE http://localhost:8080/api/judgments/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Expected Response:**

```json
{
  "message": "Judgment deleted successfully"
}
```

**Status Code:** `200 OK`

---

# 4️⃣ User Management Endpoints (🔒 Admin Only)

## 4.1 Get All Users

### GET /api/users

**Description:** Get all users (Admin only)

**cURL Command:**

```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer ADMIN_JWT_TOKEN"
```

**Expected Response:**

```json
[
  {
    "id": 1,
    "username": "rahul_lawyer",
    "email": "rahul@legal.com",
    "password": "***",
    "role": "LAWYER",
    "active": true
  },
  {
    "id": 2,
    "username": "admin_user",
    "email": "admin@legal.com",
    "password": "***",
    "role": "ADMIN",
    "active": true
  }
]
```

**Status Code:** `200 OK`

**Error Response (Not admin):**

```json
{
  "timestamp": "2025-12-27T01:12:00.123456",
  "status": 403,
  "error": "Forbidden",
  "message": "Access Denied"
}
```

**Status Code:** `403 Forbidden`

---

## 4.2 Get User by ID

### GET /api/users/{id}

**Description:** Get specific user by ID (Admin only)

**cURL Command:**

```bash
curl -X GET http://localhost:8080/api/users/1 \
  -H "Authorization: Bearer ADMIN_JWT_TOKEN"
```

**Expected Response:**

```json
{
  "id": 1,
  "username": "rahul_lawyer",
  "email": "rahul@legal.com",
  "password": "***",
  "role": "LAWYER",
  "active": true
}
```

**Status Code:** `200 OK`

---

## 4.3 Delete User

### DELETE /api/users/{id}

**Description:** Delete a user (Admin only)

**cURL Command:**

```bash
curl -X DELETE http://localhost:8080/api/users/1 \
  -H "Authorization: Bearer ADMIN_JWT_TOKEN"
```

**Expected Response:**

```json
{
  "message": "User deleted successfully"
}
```

**Status Code:** `200 OK`

---

# 5️⃣ Complete Test Workflow Script

**Save this as `test-api.sh` and run it:**

```bash
#!/bin/bash

BASE_URL="http://localhost:8080"

echo "======================================"
echo "1. Health Check"
echo "======================================"
curl $BASE_URL/
echo -e "\n\n"

echo "======================================"
echo "2. Register User"
echo "======================================"
REGISTER_RESPONSE=$(curl -s -X POST $BASE_URL/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@legal.com",
    "password": "pass123",
    "role": "LAWYER"
  }')
echo $REGISTER_RESPONSE
TOKEN=$(echo $REGISTER_RESPONSE | grep -o '"token":"[^"]*' | grep -o '[^"]*$')
echo -e "\n\nSaved Token: $TOKEN\n\n"

echo "======================================"
echo "3. Upload Judgment 1"
echo "======================================"
curl -X POST $BASE_URL/api/judgments \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "caseName": "Maneka Gandhi vs Union of India",
    "court": "Supreme Court",
    "year": 1978,
    "judgmentText": "The right to life under Article 21 includes the right to live with human dignity.",
    "keywords": "Article 21, Right to Life",
    "verdict": "Petition allowed"
  }'
echo -e "\n\n"

echo "======================================"
echo "4. Upload Judgment 2"
echo "======================================"
curl -X POST $BASE_URL/api/judgments \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "caseName": "K.S. Puttaswamy vs Union of India",
    "court": "Supreme Court",
    "year": 2017,
    "judgmentText": "The right to privacy is protected under Article 21.",
    "keywords": "Article 21, Privacy",
    "verdict": "Petition allowed"
  }'
echo -e "\n\n"

echo "======================================"
echo "5. Get All Judgments"
echo "======================================"
curl -X GET $BASE_URL/api/judgments \
  -H "Authorization: Bearer $TOKEN"
echo -e "\n\n"

echo "======================================"
echo "6. Compare Judgments"
echo "======================================"
curl -X POST "$BASE_URL/api/judgments/compare?id1=1&id2=2" \
  -H "Authorization: Bearer $TOKEN"
echo -e "\n\n"

echo "======================================"
echo "7. Search Judgments"
echo "======================================"
curl -X GET "$BASE_URL/api/judgments/search?term=Article%2021" \
  -H "Authorization: Bearer $TOKEN"
echo -e "\n\n"

echo "======================================"
echo "8. Get Current User"
echo "======================================"
curl -X GET $BASE_URL/api/auth/me \
  -H "Authorization: Bearer $TOKEN"
echo -e "\n\n"
```

**Run the script:**

```bash
chmod +x test-api.sh
./test-api.sh
```

---

# 6️⃣ Quick Reference Table

| Endpoint                 | Method | Auth Required | Description        |
| ------------------------ | ------ | ------------- | ------------------ |
| `/`                      | GET    | ❌ No         | Health check       |
| `/health`                | GET    | ❌ No         | Health status      |
| `/api/auth/register`     | POST   | ❌ No         | Register user      |
| `/api/auth/login`        | POST   | ❌ No         | Login user         |
| `/api/auth/me`           | GET    | ✅ Yes        | Get current user   |
| `/api/judgments`         | POST   | ✅ Yes        | Upload judgment    |
| `/api/judgments`         | GET    | ✅ Yes        | Get all judgments  |
| `/api/judgments/{id}`    | GET    | ✅ Yes        | Get judgment by ID |
| `/api/judgments/search`  | GET    | ✅ Yes        | Search judgments   |
| `/api/judgments/compare` | POST   | ✅ Yes        | Compare judgments  |
| `/api/judgments/{id}`    | DELETE | ✅ Yes        | Delete judgment    |
| `/api/users`             | GET    | ✅ Admin      | Get all users      |
| `/api/users/{id}`        | GET    | ✅ Admin      | Get user by ID     |
| `/api/users/{id}`        | DELETE | ✅ Admin      | Delete user        |

---

# 7️⃣ Postman Collection

You can import this JSON into Postman:

**Save as `judgment-comparator.postman_collection.json`:**

```json
{
  "info": {
    "name": "Judgment Comparator API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "auth": {
    "type": "bearer",
    "bearer": [
      {
        "key": "token",
        "value": "{{jwt_token}}",
        "type": "string"
      }
    ]
  },
  "item": [
    {
      "name": "Auth",
      "item": [
        {
          "name": "Register",
          "request": {
            "method": "POST",
            "header": [],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"username\": \"testuser\",\n  \"email\": \"test@legal.com\",\n  \"password\": \"pass123\",\n  \"role\": \"LAWYER\"\n}",
              "options": {
                "raw": {
                  "language": "json"
                }
              }
            },
            "url": {
              "raw": "{{base_url}}/api/auth/register",
              "host": ["{{base_url}}"],
              "path": ["api", "auth", "register"]
            }
          }
        },
        {
          "name": "Login",
          "request": {
            "method": "POST",
            "header": [],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"username\": \"testuser\",\n  \"password\": \"pass123\"\n}",
              "options": {
                "raw": {
                  "language": "json"
                }
              }
            },
            "url": {
              "raw": "{{base_url}}/api/auth/login",
              "host": ["{{base_url}}"],
              "path": ["api", "auth", "login"]
            }
          }
        }
      ]
    }
  ],
  "variable": [
    {
      "key": "base_url",
      "value": "http://localhost:8080"
    },
    {
      "key": "jwt_token",
      "value": ""
    }
  ]
}
```

This gives you a complete reference for all endpoints! 🚀

[1](https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/images/12298912/986b8f1e-c060-4c26-8da1-e7746b4450bb/image.jpg)
