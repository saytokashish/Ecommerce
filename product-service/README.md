# Product Service

This service manages product listings (CRUD operations) and uses MongoDB as its database.

## Running Locally

```sh
mvn spring-boot:run
```

## API Endpoints
- `GET /api/products` — List all products
- `GET /api/products/{id}` — Get product by ID
- `POST /api/products` — Create product
- `PUT /api/products/{id}` — Update product
- `DELETE /api/products/{id}` — Delete product

## API Documentation
- Swagger UI: http://localhost:8083/swagger-ui.html

## Health Check
- `/actuator/health` 