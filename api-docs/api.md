# Judgment Comparator API Documentation

> Base URL: `http://localhost:8080`

---

## 📋 Table of Contents

- [User Management](#user-management)
  - [Register User](#1-register-user)
- [Judgment Operations](#judgment-operations)
  - [Upload Judgment](#2-upload-judgment)
  - [Get All Judgments](#3-get-all-judgments)
  - [Compare Judgments](#4-compare-two-judgments)

---

## User Management

### 1. Register User

**Endpoint:** `POST /api/users/register`

**Request:**

```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "rahul_lawyer",
    "email": "rahul@legal.com",
    "password": "pass123",
    "role": "LAWYER"
  }'
```

**Success Response:**

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

---

## Judgment Operations

### 2. Upload Judgment

**Endpoint:** `POST /api/judgments`

**Request:**

```bash
curl -X POST http://localhost:8080/api/judgments \
  -H "Content-Type: application/json" \
  -d '{
    "caseName": "Maneka Gandhi vs Union of India",
    "court": "Supreme Court",
    "year": 1978,
    "judgmentText": "The right to life under Article 21 includes the right to live with human dignity.",
    "keywords": "Article 21, Right to Life",
    "verdict": "Petition allowed"
  }'
```

**Success Response:**

```json
{
  "id": 1,
  "caseName": "Maneka Gandhi vs Union of India",
  "court": "Supreme Court",
  "year": 1978,
  "judgmentText": "The right to life under Article 21 includes the right to live with human dignity.",
  "keywords": "Article 21, Right to Life",
  "verdict": "Petition allowed",
  "uploadedAt": "2025-12-27T00:15:30.123456"
}
```

#### Upload Second Judgment (Example)

```bash
curl -X POST http://localhost:8080/api/judgments \
  -H "Content-Type: application/json" \
  -d '{
    "caseName": "K.S. Puttaswamy vs Union of India",
    "court": "Supreme Court",
    "year": 2017,
    "judgmentText": "The right to privacy is protected as an intrinsic part of the right to life under Article 21.",
    "keywords": "Article 21, Right to Privacy",
    "verdict": "Petition allowed"
  }'
```

---

### 3. Get All Judgments

**Endpoint:** `GET /api/judgments`

**Request:**

```bash
curl http://localhost:8080/api/judgments
```

---

### 4. Compare Two Judgments ⭐

> **Core Feature!** Compare two judgments to analyze their similarities.

**Endpoint:** `POST /api/judgments/compare`

**Query Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `id1` | Long | ID of the first judgment |
| `id2` | Long | ID of the second judgment |

**Request:**

```bash
curl -X POST "http://localhost:8080/api/judgments/compare?id1=1&id2=2"
```

**Success Response:**

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

---

## Response Fields

### Analysis Object

| Field                  | Type    | Description                                     |
| ---------------------- | ------- | ----------------------------------------------- |
| `similarityPercentage` | Double  | Percentage of text similarity between judgments |
| `commonKeywords`       | Array   | List of keywords found in both judgments        |
| `sameVerdictType`      | Boolean | Whether both judgments have the same verdict    |
| `yearDifference`       | Integer | Number of years between the two judgments       |

---

## Available Roles

| Role     | Description          |
| -------- | -------------------- |
| `LAWYER` | Legal professional   |
| `JUDGE`  | Judicial officer     |
| `ADMIN`  | System administrator |

---

_Last updated: December 2025_
