# Microservices Demo System

This project demonstrates a simple microservices-based architecture using Java, Spring Boot, and Docker. It consists of three independent services:

- **User Service**: Manages user accounts (CRUD, H2 in-memory DB)
- **Order Service**: Handles orders (CRUD, MySQL/PostgreSQL)
- **Product Service**: Manages product listings (CRUD, MongoDB)

## Project Structure

- `user-service/` — User microservice
- `order-service/` — Order microservice
- `product-service/` — Product microservice
- `docker-compose.yml` — Orchestrates all services and databases

## Prerequisites
- Java 17+
- Maven 3.8+
- Docker & Docker Compose

## Setup & Run

1. **Build all services:**
   ```sh
   mvn clean install
   ```
2. **Start all services and databases:**
   ```sh
   docker-compose up --build
   ```
3. **Access Services:**
   - User Service: http://localhost:8081
   - Order Service: http://localhost:8082
   - Product Service: http://localhost:8083

## API Documentation
Each service exposes Swagger UI:
- User Service: http://localhost:8081/swagger-ui.html
- Order Service: http://localhost:8082/swagger-ui.html
- Product Service: http://localhost:8083/swagger-ui.html

## Health Checks
Each service exposes a health endpoint:
- `/actuator/health`

## Testing
You can use Postman or curl to test the endpoints. Example requests are provided in each service's README.

---

For detailed service-specific instructions, see the README in each service module.