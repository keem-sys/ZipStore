# ZipStore Backend API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Stripe](https://img.shields.io/badge/Stripe-626CD9?style=for-the-badge&logo=Stripe&logoColor=white)
![Resend](https://img.shields.io/badge/Resend-black?style=for-the-badge&logo=resend&logoColor=white)
![Koyeb](https://img.shields.io/badge/Koyeb-011627?style=for-the-badge&logo=koyeb&logoColor=white)

ZipStore is a full-stack e-commerce platform. 
This repository contains the **RESTful API** 
built with Java and Spring Boot, designed for high performance, 
stateless authentication, and secure payment processing.

[**Explore the Live Store →**](https://zipstore-shop.vercel.app/)

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
*   **Repository Layer:** Manages database interactions using Spring Data JPA and Hibernate.
*   **Security:** Stateless session management using Spring Security 6 and JWT.

---

## Key Features

*    Secure Registration and Login with JWT and Bcrypt password encoding.
*    Browse, search, and view product details.
*    Payment processing integration via **Stripe**.
*    Persistent tracking of user purchases and order status.
*    Automated contact form and notification emails powered by **Resend**.

---

## Tech Stack

| Component      | Technology                  |
|:---------------|:----------------------------|
| **Language**   | Java 21 (LTS)               |
| **Framework**  | Spring Boot 3+              |
| **Database**   | PostgreSQL (Neon.tech)      |
| **ORM**        | Hibernate / Spring Data JPA |
| **Security**   | Spring Security 6 + JJWT    |
| **Payments**   | Stripe API                  |
| **Email**      | Resend API                  |
| **Build Tool** | Maven                       |
| **Deployment** | Koyeb                       |

---

## Environment Variables

To run this project, you need to configure the following variables in your `application.properties` or 
your cloud dashboard.

| Variable         | Description                    | Example                                    |
|:-----------------|:-------------------------------|:-------------------------------------------|
| `DATABASE_URL`   | JDBC Connection String         | `jdbc:postgresql://ep-id.aws.neon.tech/db` |
| `DB_USERNAME`    | Database Username              | `neondb_owner`                             |
| `DB_PASSWORD`    | Database Password              | `••••••••••••`                             |
| `JWT_SECRET`     | Secret key for signing tokens  | `256-bit-random-string`                    |
| `STRIPE_SECRET`  | Stripe Secret Key (Test/Live)  | `sk_test_...`                              |
| `RESEND_API_KEY` | API Key from Resend            | `re_123...`                                |
| `ADMIN_EMAIL`    | Email to receive contact forms | `admin@zipstore.co.za`                     |

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

*   **Backend:** Hosted on **Koyeb**
*   **Database:** Serverless PostgreSQL via **Neon.tech**.
*   **CI/CD:** Automatic deployments triggered on every `git push` to the main branch.

---

## License

Distributed under the Apache License. See `LICENSE` for more information.