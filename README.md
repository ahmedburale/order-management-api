# Order Management API — Spring Boot

Production-ready REST API for managing customer orders built with Spring Boot 3, PostgreSQL, and Drizzle-style patterns.

## Stack

- **Java 21** + **Spring Boot 3.3**
- **Spring Data JPA** + **Hibernate** + **PostgreSQL**
- **Lombok** — reduces boilerplate
- **Jakarta Validation** — request validation
- **springdoc-openapi** — Swagger UI at `/api/docs`
- **Spring Boot Actuator** — health endpoints for K8s

## Open in IntelliJ IDEA

1. **File → Open** — select the `order-management-api/` folder
2. IntelliJ detects Maven automatically and imports the project
3. Set JDK 21: **File → Project Structure → SDK**

## Prerequisites

- Java 21 (`brew install openjdk@21` on Mac)
- PostgreSQL running locally
- Maven (bundled with IntelliJ or `brew install maven`)

## Database Setup

```sql
-- In psql or any PostgreSQL client:
CREATE DATABASE orders;
```

Hibernate auto-creates the `orders` table on first boot (`spring.jpa.hibernate.ddl-auto=update`).

## Run Locally

### Option A — IntelliJ Run Config

1. Open `OrderManagementApiApplication.java`
2. Click the green ▶ button
3. Set environment variables in **Run → Edit Configurations → Environment Variables**:
   ```
   DATABASE_URL=jdbc:postgresql://localhost:5432/orders
   DB_USERNAME=postgres
   DB_PASSWORD=your_password
   ```

### Option B — Terminal

```bash
export DATABASE_URL=jdbc:postgresql://localhost:5432/orders
export DB_USERNAME=postgres
export DB_PASSWORD=your_password

mvn spring-boot:run
```

### Option C — Local profile

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
# Edit src/main/resources/application-local.properties with your credentials first
```

## API Endpoints

| Method   | Path                  | Description                        |
|----------|-----------------------|------------------------------------|
| GET      | `/api/healthz`        | Health check (K8s probe)           |
| GET      | `/api/orders`         | List all orders (`?status=` filter)|
| POST     | `/api/orders`         | Create a new order                 |
| GET      | `/api/orders/summary` | Aggregate counts by status         |
| GET      | `/api/orders/{id}`    | Get a single order                 |
| PATCH    | `/api/orders/{id}`    | Update order fields / status       |
| DELETE   | `/api/orders/{id}`    | Delete an order (204)              |

## Swagger UI

Open **http://localhost:8080/api/docs** in your browser after starting the server.

## Order Lifecycle

```
PENDING → PROCESSING → SHIPPED → DELIVERED
                ↘           ↘
              CANCELLED   CANCELLED
```

Use `PATCH /api/orders/{id}` with `{ "status": "PROCESSING" }` to advance the lifecycle.

## Example Requests

```bash
# Create
curl -s -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"customerName":"Alice","product":"Keyboard","quantity":1}'

# List with status filter
curl -s "http://localhost:8080/api/orders?status=PENDING"

# Summary
curl -s http://localhost:8080/api/orders/summary

# Advance status
curl -s -X PATCH http://localhost:8080/api/orders/1 \
  -H "Content-Type: application/json" \
  -d '{"status":"PROCESSING"}'
```

## Build & Docker

```bash
# Build JAR
mvn package -DskipTests

# Build Docker image
docker build -t order-management-api:latest .

# Run container
docker run -p 8080:8080 \
  -e DATABASE_URL=jdbc:postgresql://host.docker.internal:5432/orders \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=your_password \
  order-management-api:latest
```

## Project Structure

```
src/
└── main/
    ├── java/com/orderapi/
    │   ├── OrderManagementApiApplication.java   ← Entry point
    │   ├── config/
    │   │   └── OpenApiConfig.java               ← Swagger config
    │   ├── controller/
    │   │   ├── OrderController.java             ← REST endpoints
    │   │   └── HealthController.java            ← /api/healthz
    │   ├── dto/
    │   │   ├── CreateOrderRequest.java
    │   │   ├── UpdateOrderRequest.java
    │   │   ├── OrderResponse.java
    │   │   └── OrderSummaryResponse.java
    │   ├── entity/
    │   │   └── Order.java                       ← JPA entity
    │   ├── enums/
    │   │   └── OrderStatus.java                 ← PENDING/PROCESSING/...
    │   ├── exception/
    │   │   ├── OrderNotFoundException.java
    │   │   └── GlobalExceptionHandler.java      ← RFC 9457 ProblemDetail
    │   ├── repository/
    │   │   └── OrderRepository.java             ← Spring Data JPA
    │   └── service/
    │       └── OrderService.java                ← Business logic
    └── resources/
        ├── application.properties               ← Main config
        └── application-local.properties         ← Local dev overrides
```
