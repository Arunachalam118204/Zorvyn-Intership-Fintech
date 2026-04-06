# Finance Data Processing and Access Control Backend

A fintech-themed Spring Boot backend that simulates a simplified internal finance operations system.
Built as a backend internship screening assignment demonstration.

---

## Project Overview

This backend models a **Finance Dashboard System** where different users interact with financial records based on their assigned role. The system enforces role-based access control, provides financial summaries, and supports full CRUD operations on financial records.

---

## Features

- Role-based access control via `X-USER-ROLE` request header
- User management (create, view, update role, activate/deactivate)
- Financial record management (create, read, update, delete, filter)
- Dashboard summary with total income, total expense, net balance, category totals, and latest 5 transactions
- Input validation with clear error messages
- Global exception handling with consistent JSON error responses

---

## Tech Stack

| Technology         | Purpose                        |
|--------------------|--------------------------------|
| Java 17            | Programming language           |
| Spring Boot 3.2.3  | Application framework          |
| Spring Web         | REST API layer                 |
| Spring Data JPA    | Database access layer          |
| H2 Database        | In-memory database             |
| Jakarta Validation | Input validation               |
| Maven              | Build tool                     |

---

## How to Run in IntelliJ IDEA

1. **Clone or download** this project folder
2. Open IntelliJ IDEA → **File → Open** → select the `finance-backend` folder
3. Wait for Maven to download dependencies (bottom progress bar)
4. Open `FinanceBackendApplication.java`
5. Click the **green Run button** or press `Shift + F10`
6. The server starts at: `http://localhost:8080`

> **Java 17 required.** Check via `java -version` in terminal.

---

## H2 Database Console

While the app is running, visit:

```
http://localhost:8080/h2-console
```

Use these credentials:
- **JDBC URL:** `jdbc:h2:mem:financedb`
- **Username:** `sa`
- **Password:** _(leave blank)_

You can run SQL queries here to inspect data directly.

---

## API Reference

### User Endpoints — ADMIN only
| Method | Endpoint                    | Description           |
|--------|-----------------------------|-----------------------|
| POST   | `/api/users`                | Create a new user     |
| GET    | `/api/users`                | Get all users         |
| GET    | `/api/users/{id}`           | Get user by ID        |
| PUT    | `/api/users/{id}/role`      | Update user's role    |
| PUT    | `/api/users/{id}/status`    | Activate/deactivate   |

### Financial Record Endpoints
| Method | Endpoint                    | Access         | Description           |
|--------|-----------------------------|----------------|-----------------------|
| POST   | `/api/records`              | ADMIN          | Create a record       |
| GET    | `/api/records`              | ADMIN, ANALYST | Get all records       |
| GET    | `/api/records/{id}`         | ADMIN, ANALYST | Get record by ID      |
| PUT    | `/api/records/{id}`         | ADMIN          | Update a record       |
| DELETE | `/api/records/{id}`         | ADMIN          | Delete a record       |
| GET    | `/api/records/filter`       | ADMIN, ANALYST | Filter records        |

Filter query params: `type`, `category`, `from` (yyyy-MM-dd), `to` (yyyy-MM-dd)

### Dashboard Endpoint
| Method | Endpoint                    | Access              | Description         |
|--------|-----------------------------|---------------------|---------------------|
| GET    | `/api/dashboard/summary`    | ADMIN, ANALYST, VIEWER | Financial summary |

---

## Header Usage

Every request **must** include the `X-USER-ROLE` header:

```
X-USER-ROLE: ADMIN
X-USER-ROLE: ANALYST
X-USER-ROLE: VIEWER
```

---

## Sample Request Bodies

### Create User
```json
{
  "name": "Riya Sharma",
  "role": "ANALYST"
}
```

### Create Financial Record
```json
{
  "amount": 15000.00,
  "type": "INCOME",
  "category": "Salary",
  "date": "2024-03-01",
  "notes": "March salary credit"
}
```

### Update Role
```json
{
  "role": "VIEWER"
}
```

### Update Status
```json
{
  "active": false
}
```

---

## Error Response Format

All errors return a consistent JSON structure:

```json
{
  "timestamp": "2024-03-01T10:30:00",
  "status": 403,
  "error": "Forbidden",
  "message": "Access denied. This action requires ADMIN role.",
  "path": "/api/users"
}
```

---

## Design Decisions

- **No Spring Security** — Role checking is done manually via `X-USER-ROLE` header in a utility class. Simple and easy to explain.
- **H2 In-Memory Database** — No setup needed. Perfect for running demos and interviews.
- **No Lombok** — All getters/setters written manually for full transparency and easier learning.
- **Constructor Injection** — Used instead of `@Autowired` on fields, which is the recommended Spring practice.
- **Global Exception Handler** — All exceptions are handled centrally using `@RestControllerAdvice`.
- **BigDecimal for money** — Financial amounts use `BigDecimal` to avoid floating-point precision issues.

---

## Assumptions

- Authentication is simulated via the `X-USER-ROLE` header. In a real system, this would be handled by Spring Security + JWT.
- The H2 database resets every time the application restarts (in-memory mode).
- A user's "active" flag is stored but not enforced in API access decisions — it is a data field for user management purposes.
- Category totals in the dashboard aggregate all record types (INCOME + EXPENSE) together per category.

---

## Future Improvements

- Add Spring Security with JWT for real authentication
- Use PostgreSQL or MySQL for persistent storage
- Add pagination to list endpoints
- Add audit fields (createdAt, updatedAt) to entities
- Add unit and integration tests
- Add Swagger/OpenAPI documentation
