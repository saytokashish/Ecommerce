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
- User Service: http://localhost:8081/swagger-ui/index.html
- Order Service: http://localhost:8082/swagger-ui/index.html
- Product Service: http://localhost:8083/swagger-ui/index.html

## Health Checks
Each service exposes a health endpoint:
- http://localhost:8083/actuator/health
- http://localhost:8083/actuator/info
- http://localhost:8083/actuator/metrics

## Testing
You can use Swagger-UI to test the endpoints.

---
## In Short 
1. In root directory run docker-compose up --build
2. Test the Swagger-UI