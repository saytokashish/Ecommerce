# Order Service

This service handles orders placed by users (CRUD operations) and uses a relational database (MySQL or PostgreSQL).

## Running Locally

```sh
mvn spring-boot:run
```

## API Endpoints
- `GET /api/orders` — List all orders
- `GET /api/orders/{id}` — Get order by ID
- `POST /api/orders` — Create order
- `PUT /api/orders/{id}` — Update order
- `DELETE /api/orders/{id}` — Delete order

## API Documentation
- Swagger UI: http://localhost:8082/swagger-ui.html

## Health Check
- `/actuator/health` 