# Customer Management API

A RESTful Customer Management API built using **Java (Spring Boot)** with PostgreSQL and Docker support.

This project implements a clean layered architecture with Controller → Service → DAO (Repository) separation and is fully containerized using Docker.

---

## 🚀 Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- Gradle
- PostgreSQL
- Docker
- Docker Compose

---

## 📌 Features

- Create customer
- Get all customers
- Get customer by ID
- Update customer
- Soft delete customer (logical delete)
- Input validation
- Layered architecture (Controller / Service / DAO)
- PostgreSQL persistence
- Fully Dockerized setup

---

## 🏗 Architecture

- **Controller** → REST APIs
- **Service** → Business logic
- **Repository** → Database operations only

---

## 📊 Customer Entity

| Field | Type | Description |
|------|------|-------------|
| id | UUID | Primary key |
| first_name | String | Required |
| last_name | String | Required |
| email | String | Unique, required |
| phone_number | String | Optional |
| date_of_birth | Date | Required |
| address | String | Optional |
| account_status | Enum | ACTIVE / INACTIVE / SUSPENDED |
| credit_score | Integer | 300–850 |
| created_at | DateTime | Auto-generated |
| deleted | Boolean | Soft delete flag |

---

## 🔗 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/customers` | Create customer |
| GET | `/customers` | Get all customers |
| GET | `/customers/{id}` | Get customer by ID |
| PUT | `/customers/{id}` | Update customer |
| DELETE | `/customers/{id}` | Soft delete customer |

---

## 🐳 Run with Docker (Recommended)

### Step 1: Build and run containers

```bash id="docker1"
docker-compose up --build
