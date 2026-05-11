# Order Management API

A production-ready REST API for managing customer orders, built with **Spring Boot 3.3**, **Java 21**, and **PostgreSQL**.

## Features

- Full CRUD operations for orders
- Order lifecycle: `PENDING` -> `PROCESSING` -> `SHIPPED` -> `DELIVERED` (or `CANCELLED`)
- Aggregate summary endpoint for dashboard analytics
- Interactive API documentation via Swagger UI
- Global exception handling with structured error responses
- Docker and Docker Compose support

## Tech Stack

| Technology | Version |
|-----------|---------|
| Spring Boot | 3.3.5 |
| Java | 21 (LTS) |
| PostgreSQL | 15+ |
| Spring Data JPA / Hibernate | 6.5 |
| SpringDoc OpenAPI | 2.6 |
| Maven | 3.9+ |

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/healthz` | Health check |
| GET | `/api/orders` | List all orders |
| POST | `/api/orders` | Create order |
| GET | `/api/orders/{id}` | Get order by ID |
| PUT | `/api/orders/{id}` | Update order |
| DELETE | `/api/orders/{id}` | Delete order |
| GET | `/api/orders/summary` | Status count summary |
| GET | `/api/docs` | Swagger UI |

## Quick Start

### Prerequisites

- Java 21 (Eclipse Temurin)
- Maven 3.9+
- PostgreSQL (local or Docker)

### Run Locally

1. Create the database:
   ```bash
   psql -U postgres -c "CREATE DATABASE orders;"
   ```

2. Set environment variables:
   ```bash
   export DB_USERNAME=postgres
   export DB_PASSWORD=your_password
   export DATABASE_URL=jdbc:postgresql://localhost:5432/orders
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Open Swagger UI: http://localhost:9090/api/docs

### Run with Docker

```bash
docker-compose up --build
```

Services:
- API: http://localhost:9090
- Swagger: http://localhost:9090/api/docs
- Postgres: localhost:5434

### Stop Docker

```bash
docker-compose down
```

## Project Structure

```
src/main/java/com/orderapi/
  -- OrderManagementApiApplication.java
  -- config/
  |   -- OpenApiConfig.java
  -- controller/
  |   -- OrderController.java
  -- dto/
  |   -- OrderRequest.java
  |   -- OrderResponse.java
  |   -- OrderSummaryResponse.java
  -- entity/
  |   -- Order.java
  -- exception/
  |   -- OrderNotFoundException.java
  |   -- GlobalExceptionHandler.java
  -- repository/
  |   -- OrderRepository.java
  -- service/
      -- OrderService.java
```

## Database Schema

| Column | Type | Constraints |
|--------|------|-------------|
| `id` | `BIGSERIAL` | PRIMARY KEY |
| `customer_name` | `VARCHAR(255)` | NOT NULL |
| `product` | `VARCHAR(255)` | NOT NULL |
| `quantity` | `INTEGER` | NOT NULL, CHECK > 0 |
| `status` | `VARCHAR(20)` | NOT NULL, DEFAULT 'PENDING' |
| `created_at` | `TIMESTAMP` | DEFAULT NOW() |
| `updated_at` | `TIMESTAMP` | Auto-updated |

### Order Status Values

- `PENDING` - Awaiting processing
- `PROCESSING` - Being prepared
- `SHIPPED` - Dispatched
- `DELIVERED` - Received by customer
- `CANCELLED` - Order cancelled

## Security

- No hardcoded credentials in committed files
- Database credentials via environment variables
- `application-local.properties` is gitignored
- Docker container runs as non-root user

## License

MIT
