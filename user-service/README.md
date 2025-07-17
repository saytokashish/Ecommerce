# User Service

This service manages user accounts (CRUD operations) and uses an in-memory H2 database.

## Running Locally

```sh
mvn spring-boot:run
```

## API Endpoints
- `GET /api/users` — List all users
- `GET /api/users/{id}` — Get user by ID
- `POST /api/users` — Create user
- `PUT /api/users/{id}` — Update user
- `DELETE /api/users/{id}` — Delete user

## API Documentation
- Swagger UI: http://localhost:8081/swagger-ui.html

## Health Check
- `/actuator/health` 