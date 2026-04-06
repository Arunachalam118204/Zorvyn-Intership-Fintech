# Postman Testing Guide — Finance Backend

## Setup
- Base URL: `http://localhost:8080`
- Every request needs the header: `X-USER-ROLE` with value `ADMIN`, `ANALYST`, or `VIEWER`
- Content-Type: `application/json` (for POST and PUT requests)

---

## STEP 1 — Create Users (ADMIN only)

### Create an ADMIN user
POST http://localhost:8080/api/users
Header: X-USER-ROLE: ADMIN
Body:
{
  "name": "Arun Kumar",
  "role": "ADMIN"
}
Expected: 201 Created

---

### Create an ANALYST user
POST http://localhost:8080/api/users
Header: X-USER-ROLE: ADMIN
Body:
{
  "name": "Priya Singh",
  "role": "ANALYST"
}
Expected: 201 Created

---

### Create a VIEWER user
POST http://localhost:8080/api/users
Header: X-USER-ROLE: ADMIN
Body:
{
  "name": "Ravi Nair",
  "role": "VIEWER"
}
Expected: 201 Created

---

## STEP 2 — View Users (ADMIN only)

### Get all users
GET http://localhost:8080/api/users
Header: X-USER-ROLE: ADMIN
Expected: 200 OK with list of users

---

### Get user by ID
GET http://localhost:8080/api/users/1
Header: X-USER-ROLE: ADMIN
Expected: 200 OK with user data

---

### Try as ANALYST — should be denied
GET http://localhost:8080/api/users
Header: X-USER-ROLE: ANALYST
Expected: 403 Forbidden

---

## STEP 3 — Create Financial Records (ADMIN only)

### Record 1 — Salary Income
POST http://localhost:8080/api/records
Header: X-USER-ROLE: ADMIN
Body:
{
  "amount": 50000.00,
  "type": "INCOME",
  "category": "Salary",
  "date": "2024-03-01",
  "notes": "March monthly salary"
}
Expected: 201 Created

---

### Record 2 — Freelance Income
POST http://localhost:8080/api/records
Header: X-USER-ROLE: ADMIN
Body:
{
  "amount": 12000.00,
  "type": "INCOME",
  "category": "Freelance",
  "date": "2024-03-05",
  "notes": "Website project payment"
}
Expected: 201 Created

---

### Record 3 — Rent Expense
POST http://localhost:8080/api/records
Header: X-USER-ROLE: ADMIN
Body:
{
  "amount": 18000.00,
  "type": "EXPENSE",
  "category": "Rent",
  "date": "2024-03-07",
  "notes": "March rent payment"
}
Expected: 201 Created

---

### Record 4 — Groceries Expense
POST http://localhost:8080/api/records
Header: X-USER-ROLE: ADMIN
Body:
{
  "amount": 4500.00,
  "type": "EXPENSE",
  "category": "Groceries",
  "date": "2024-03-10",
  "notes": "Weekly grocery shopping"
}
Expected: 201 Created

---

### Record 5 — Investment Income
POST http://localhost:8080/api/records
Header: X-USER-ROLE: ADMIN
Body:
{
  "amount": 8000.00,
  "type": "INCOME",
  "category": "Investment",
  "date": "2024-03-15",
  "notes": "Mutual fund returns"
}
Expected: 201 Created

---

### Record 6 — Utilities Expense
POST http://localhost:8080/api/records
Header: X-USER-ROLE: ADMIN
Body:
{
  "amount": 2200.00,
  "type": "EXPENSE",
  "category": "Utilities",
  "date": "2024-03-20",
  "notes": "Electricity and water bill"
}
Expected: 201 Created

---

## STEP 4 — Read Records

### Get all records as ANALYST
GET http://localhost:8080/api/records
Header: X-USER-ROLE: ANALYST
Expected: 200 OK

---

### Get all records as ADMIN
GET http://localhost:8080/api/records
Header: X-USER-ROLE: ADMIN
Expected: 200 OK

---

### Get record by ID
GET http://localhost:8080/api/records/1
Header: X-USER-ROLE: ANALYST
Expected: 200 OK

---

### Try to read records as VIEWER — should be denied
GET http://localhost:8080/api/records
Header: X-USER-ROLE: VIEWER
Expected: 403 Forbidden

---

## STEP 5 — Filter Records

### Filter by type INCOME
GET http://localhost:8080/api/records/filter?type=INCOME
Header: X-USER-ROLE: ANALYST
Expected: 200 OK — only income records

---

### Filter by type EXPENSE
GET http://localhost:8080/api/records/filter?type=EXPENSE
Header: X-USER-ROLE: ANALYST
Expected: 200 OK — only expense records

---

### Filter by category
GET http://localhost:8080/api/records/filter?category=Salary
Header: X-USER-ROLE: ADMIN
Expected: 200 OK — only Salary records

---

### Filter by date range
GET http://localhost:8080/api/records/filter?from=2024-03-01&to=2024-03-10
Header: X-USER-ROLE: ANALYST
Expected: 200 OK — records in that date range

---

### Filter by type and date range
GET http://localhost:8080/api/records/filter?type=EXPENSE&from=2024-03-01&to=2024-03-31
Header: X-USER-ROLE: ADMIN
Expected: 200 OK — only expense records in March

---

## STEP 6 — Dashboard Summary

### View as ADMIN
GET http://localhost:8080/api/dashboard/summary
Header: X-USER-ROLE: ADMIN
Expected: 200 OK with totalIncome, totalExpense, netBalance, categoryTotals, recentTransactions

---

### View as ANALYST
GET http://localhost:8080/api/dashboard/summary
Header: X-USER-ROLE: ANALYST
Expected: 200 OK

---

### View as VIEWER
GET http://localhost:8080/api/dashboard/summary
Header: X-USER-ROLE: VIEWER
Expected: 200 OK

---

## STEP 7 — Update and Delete (ADMIN only)

### Update a record
PUT http://localhost:8080/api/records/1
Header: X-USER-ROLE: ADMIN
Body:
{
  "amount": 55000.00,
  "type": "INCOME",
  "category": "Salary",
  "date": "2024-03-01",
  "notes": "March salary — corrected amount"
}
Expected: 200 OK

---

### Delete a record
DELETE http://localhost:8080/api/records/6
Header: X-USER-ROLE: ADMIN
Expected: 200 OK with success message

---

### Try to delete as ANALYST — should be denied
DELETE http://localhost:8080/api/records/5
Header: X-USER-ROLE: ANALYST
Expected: 403 Forbidden

---

## STEP 8 — User Management

### Update user role
PUT http://localhost:8080/api/users/3/role
Header: X-USER-ROLE: ADMIN
Body:
{
  "role": "ANALYST"
}
Expected: 200 OK

---

### Deactivate a user
PUT http://localhost:8080/api/users/3/status
Header: X-USER-ROLE: ADMIN
Body:
{
  "active": false
}
Expected: 200 OK

---

## STEP 9 — Validation Error Tests

### Missing required field — should fail
POST http://localhost:8080/api/records
Header: X-USER-ROLE: ADMIN
Body:
{
  "type": "INCOME",
  "category": "Salary",
  "date": "2024-03-01"
}
Expected: 400 Bad Request — "Amount is required"

---

### Negative amount — should fail
POST http://localhost:8080/api/records
Header: X-USER-ROLE: ADMIN
Body:
{
  "amount": -500,
  "type": "INCOME",
  "category": "Test",
  "date": "2024-03-01"
}
Expected: 400 Bad Request — "Amount must be greater than zero"

---

### Invalid role in header — should fail
GET http://localhost:8080/api/records
Header: X-USER-ROLE: SUPERUSER
Expected: 400 Bad Request — "Invalid role"

---

### Resource not found
GET http://localhost:8080/api/records/9999
Header: X-USER-ROLE: ADMIN
Expected: 404 Not Found

---

## Dashboard Sample Response

{
  "totalIncome": 70000.00,
  "totalExpense": 24700.00,
  "netBalance": 45300.00,
  "categoryTotals": {
    "Salary": 55000.00,
    "Freelance": 12000.00,
    "Investment": 8000.00,
    "Rent": 18000.00,
    "Groceries": 4500.00,
    "Utilities": 2200.00
  },
  "recentTransactions": [
    { "id": 5, "amount": 8000.00, "type": "INCOME", "category": "Investment", "date": "2024-03-15", "notes": "Mutual fund returns" },
    ...
  ]
}
