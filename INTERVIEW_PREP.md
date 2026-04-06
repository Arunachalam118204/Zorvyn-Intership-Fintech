# Interview Preparation — Finance Backend Project

---

## Your 60-Second Project Explanation (Memorize This)

"I built a Finance Data Processing and Access Control Backend using Java and Spring Boot.
The system manages financial records — like income and expense transactions — for a finance dashboard.
It has three user roles: Admin, Analyst, and Viewer.
Admin has full access, Analyst can view records and the dashboard, and Viewer can only see the dashboard summary.
I implemented role-based access using a custom request header called X-USER-ROLE, which is checked in a utility class before any business logic runs.
The dashboard provides real-time financial insights like total income, total expense, net balance, category-wise totals, and the latest five transactions.
I used H2 as an in-memory database for easy setup, Spring Data JPA for database operations, and a global exception handler to return consistent error responses.
The project is structured with separate layers — controller, service, repository, model, DTO, exception, and util — following a clean MVC-style architecture."

---

## 10 Likely Interview Questions with Beginner-Safe Answers

---

### Q1. Can you walk me through your project?

**Answer:**
"My project is a backend for a finance dashboard system. It has three user roles — Admin, Analyst, and Viewer — each with different levels of access. Admin manages users and records. Analyst can view records and the dashboard. Viewer can only see the dashboard summary. I used Spring Boot for the backend framework, Spring Data JPA for database access, and H2 as an in-memory database. The project is structured in layers: controller handles HTTP requests, service contains business logic, and repository handles database queries."

---

### Q2. How did you implement role-based access control without Spring Security?

**Answer:**
"I used a custom request header called X-USER-ROLE. Each API request must include this header with a value of ADMIN, ANALYST, or VIEWER. I created a utility class called RoleAccessUtil that reads this header and checks if the role is allowed for that endpoint. For example, the delete record endpoint calls requireAdmin(), which throws an AccessDeniedException if the role is not ADMIN. This approach is simple, easy to understand, and easy to test without any framework complexity."

---

### Q3. What is Spring Data JPA and why did you use it?

**Answer:**
"Spring Data JPA is a part of the Spring framework that makes database operations much easier. Instead of writing SQL queries manually, I define repository interfaces that extend JpaRepository, and Spring automatically provides methods like save(), findById(), findAll(), and delete(). I also used method name conventions like findByType() or findByDateBetween() which Spring automatically translates into the right SQL query. It saves a lot of boilerplate code."

---

### Q4. Why did you use BigDecimal instead of double for financial amounts?

**Answer:**
"In finance, precision is very important. If you use double or float, you can get small rounding errors due to how floating-point numbers work in binary. For example, 0.1 + 0.2 in double gives 0.30000000000000004. BigDecimal avoids this problem by storing exact decimal values. For a finance application, even small precision errors are unacceptable, so BigDecimal is the correct choice."

---

### Q5. What is the purpose of the DTO classes?

**Answer:**
"DTO stands for Data Transfer Object. I use DTOs to separate what the API accepts as input from the actual database entity. For example, when creating a financial record, the client sends a FinanceRecordRequest DTO. I then map that data to a FinanceRecord entity and save it. This is good practice because it lets me validate only the fields I want, hide internal fields from the API response, and keep the entity and the API contract independent of each other."

---

### Q6. How does your global exception handler work?

**Answer:**
"I created a class called GlobalExceptionHandler annotated with @RestControllerAdvice. This means Spring automatically routes exceptions thrown anywhere in the application to the methods in this class. Each method handles a specific exception type. For example, when a ResourceNotFoundException is thrown, it returns a 404 response with a JSON body containing the timestamp, status, error type, message, and request path. This gives consistent error responses across the entire API."

---

### Q7. What does @Valid do in your controllers?

**Answer:**
"@Valid tells Spring to run the Jakarta Validation constraints on the incoming request body before the controller method executes. For example, in FinanceRecordRequest, I have @NotNull on amount and @DecimalMin on the value. If validation fails — like if amount is missing — Spring throws a MethodArgumentNotValidException. My global exception handler catches that and returns a 400 Bad Request response with the specific error message."

---

### Q8. Why did you choose H2 database?

**Answer:**
"H2 is an in-memory database that runs inside the Java application itself — no external database installation needed. It's perfect for assignments, demos, and development because anyone can clone the project and run it immediately. The data is recreated fresh every time the app starts. I also enabled the H2 console so the database can be inspected visually via a browser. In a production system, I would replace H2 with PostgreSQL or MySQL."

---

### Q9. What is the difference between @RestController and @Controller?

**Answer:**
"@Controller is the basic Spring MVC annotation that marks a class as a web controller. By default, it expects you to return a view name (like an HTML page). @RestController is a shortcut that combines @Controller and @ResponseBody, which means every method automatically serializes the return value to JSON and writes it directly to the HTTP response body. Since this is a REST API, I used @RestController everywhere — no view templates needed."

---

### Q10. If you had more time, what would you improve?

**Answer:**
"First, I would replace the header-based role check with real authentication using Spring Security and JWT tokens so each user has a proper login session. Second, I would switch from H2 to PostgreSQL for persistent storage. Third, I would add pagination to the GET all endpoints so large datasets don't overwhelm the API. Fourth, I would add audit fields like createdAt and updatedAt to the entities to track when records were created or modified. Fifth, I would write unit tests using JUnit and Mockito to verify the service layer logic."

---

## What NOT to Say

- Do NOT say "I don't know Spring Security" — say "I intentionally used a simpler approach for this assignment scope"
- Do NOT say "I just followed a tutorial" — say "I designed the structure based on clean layered architecture principles"
- Do NOT say "H2 is fake" — say "H2 is an in-memory database suitable for development and testing environments"
- Do NOT say "I copied this" — be confident and own every line
- Do NOT over-explain things you don't fully understand — keep answers short and clear

---

## Bonus Tips

- If asked to explain a specific class, walk through it top to bottom — annotation, then fields, then methods
- If asked about something you don't know, say: "I haven't worked with that yet, but I understand the concept and would be comfortable learning it"
- Mention that you know the next steps (Spring Security, PostgreSQL, tests) — it shows awareness
- Keep your explanation calm and structured — interviewers value clarity over speed
