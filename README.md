# ZipStore Backend API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Stripe](https://img.shields.io/badge/Stripe-626CD9?style=for-the-badge&logo=Stripe&logoColor=white)
![Resend](https://img.shields.io/badge/Resend-black?style=for-the-badge&logo=resend&logoColor=white)
![Railway](https://img.shields.io/badge/Railway-131415?style=for-the-badge&logo=railway&logoColor=white)

---

## Table of Contents

- [Architecture](#architecture)
- [Key Features](#key-features)
- [Tech Stack](#tech-stack)
- [Environment Variables](#environment-variables)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Deployment](#deployment)

---

## Architecture

This backend follows a **Controller-Service-Repository** 
architecture.

*   **Controller Layer:** Handles HTTP requests and JSON serialization.
*   **Service Layer:** Contains business logic (Order processing, Payment intent creation).
*   **Repository Layer:** Manages database interactions using JPA.
*   **Security:** Stateless authentication using JWT and Bcrypt.

---

## Key Features

*    Secure Registration and Login with JWT.
*    Browse, search, and view product details.
*    Persistent cart management for users.
*    Payment processing integration via **Stripe**.
*    Users can view past orders and status.
*    Automated contact form and notification emails powered by **Resend**.

---

## Tech Stack

| Component      | Technology                  |
|:---------------|:----------------------------|
| **Language**   | Java 21                     |
| **Framework**  | Spring Boot 3+              |
| **Database**   | PostgreSQL 16               |
| **ORM**        | Hibernate / Spring Data JPA |
| **Security**   | Spring Security 6 + JJWT    |
| **Payments**   | Stripe API                  |
| **Email**      | Resend API                  |
| **Build Tool** | Maven                       |
| **Deployment** | Docker & Railway            |

---

## Environment Variables

To run this project, you need to configure the following variables in your `application.properties` or 
your cloud environment (Railway/Docker).

| Variable         | Description                    | Example                                       |
|:-----------------|:-------------------------------|:----------------------------------------------|
| `DATABASE_URL`   | JDBC Connection String         | `jdbc:postgresql://localhost:5432/zipstoredb` |
| `DB_USERNAME`    | Database Username              | `postgres`                                    |
| `DB_PASSWORD`    | Database Password              | `password`                                    |
| `JWT_SECRET`     | Secret key for signing tokens  | `256-bit-random-string`                       |
| `STRIPE_SECRET`  | Stripe Secret Key (Test/Live)  | `sk_test_...`                                 |
| `RESEND_API_KEY` | API Key from Resend            | `re_123...`                                   |
| `ADMIN_EMAIL`    | Email to receive contact forms | `admin@zipstore.co.za`                        |

---

## Getting Started

### Prerequisites
*   Java 21 SDK installed.
*   PostgreSQL installed and running locally.
*   Maven installed.

### Installation

1.  **Clone the repository**
    ```bash
    git clone https://github.com/yourusername/zipstore-backend.git
    cd zipstore-backend
    ```

2.  **Configure Database**
    Create a local database named `zipstoredb` (or whatever you prefer) using pgAdmin or terminal.

3.  **Setup Configuration**
    Create a file named `application-secret.properties` in `src/main/resources/`
(or set environment variables in your IDE) with the credentials listed above.

4.  **Run the Application**
    ```bash
    ./mvnw spring-boot:run
    ```

The API will start on `http://localhost:8080`.

---

##  API Endpoints

The API is fully documented using **OpenAPI 3**. 
You can explore the available endpoints, view request schemas, 
and test the API directly from the browser.


| Method | Endpoint                              | Description           | Access        |
|:-------|:--------------------------------------|:----------------------|:--------------|
| `POST` | `/api/auth/register`                  | User account creation | Public        |
| `POST` | `/api/auth/login`                     | JWT Authentication    | Public        |
| `GET`  | `/api/products`                       | Fetch all products    | Public        |
| `GET`  | `/api/products/{id}`                  | Product details       | Public        |
| `POST` | `/api/orders`                         | Create new order      | Authenticated |
| `POST` | `/api/payments/create-payment-intent` | Create payment intent | Authenticated |
| `POST` | `/api/contact`                        | Send support email    | Authenticated |


---

## Deployment

This project is configured for deployment on **Railway**.

1.  **Database:** A PostgreSQL service spun up on Railway.
2.  **Backend:** The Spring Boot JAR is built using maven wrapper and deployed as a Docker container.
3.  **Communication:** The backend communicates with the database 
using Railway's private network

---

## License

Distributed under the Apache License. See `LICENSE` for more information.