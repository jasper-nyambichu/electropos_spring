# ElectroPro POS — Backend API

A robust REST API backend for the ElectroPro Point of Sale system, built for an electronics retail business in Kenya. Powers inventory management, sales processing, customer tracking, supplier management, warranty tracking, quotations, and reporting.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.1.0 |
| ORM | Hibernate / Spring Data JPA |
| Database | PostgreSQL 18 |
| Runtime Container | Docker |
| Build Tool | Maven |
| Server | Apache Tomcat (embedded) |

---

## Architecture

The backend follows a strict layered architecture with clean separation of concerns:

```
HTTP Request
    ↓
Controller        → Accepts/returns DTOs only. No business logic.
    ↓
Service           → All business logic lives here.
    ↓
Repository        → Data persistence and retrieval via JPA.
    ↓
Entity            → JPA-mapped database tables.
```

**Mappers** sit between the Service and Entity layers, converting DTOs to Entities and Entities back to response DTOs. Entities never cross the HTTP boundary.

---

## Project Structure

```
src/main/java/com/electropro/electropos/
│
├── entity/               # JPA entities (DB table mappings)
│   ├── Product.java
│   ├── Category.java
│   ├── Customer.java
│   ├── Sale.java
│   ├── SaleItem.java
│   ├── Supplier.java
│   ├── PurchaseOrder.java
│   ├── PurchaseOrderItem.java
│   ├── Warranty.java
│   ├── Quotation.java
│   ├── QuotationItem.java
│   └── GiftCard.java
│
├── controller/           # REST controllers — HTTP layer
├── service/              # Business logic layer
├── repository/           # Spring Data JPA repositories
├── dto/                  # Request and response data transfer objects
├── mapper/               # Entity ↔ DTO converters
│
└── ElectroposApplication.java
```

---

## Domain Overview

| Domain | Description |
|---|---|
| **Products** | Inventory items with pricing, stock levels, SKU, and barcode |
| **Categories** | Product groupings for organization and reporting |
| **Customers** | Customer records linked to sales and warranties |
| **Sales** | POS transactions with line items, payment method, and receipts |
| **Purchases** | Purchase orders from suppliers to restock inventory |
| **Suppliers** | Supplier contact and order management |
| **Warranties** | Product warranty tracking per customer |
| **Quotations** | Draft quotes that can be converted to sales |
| **Gift Cards** | Issuance and redemption of gift card codes |

---

## API Endpoints

### Products `/products`
| Method | Endpoint | Description |
|---|---|---|
| POST | `/products` | Add a new product |
| GET | `/products` | List all products |
| GET | `/products/{id}` | Get product by ID |
| PUT | `/products/{id}` | Update product |
| DELETE | `/products/{id}` | Delete product |
| GET | `/products/low-stock` | Products below stock threshold |
| GET | `/products/search/{name}` | Search products by name |

### Categories `/categories`
| Method | Endpoint | Description |
|---|---|---|
| POST | `/categories` | Create a category |
| GET | `/categories` | List all categories |
| GET | `/categories/{id}` | Get category with its products |
| PUT | `/categories/{id}` | Update category |
| DELETE | `/categories/{id}` | Delete category |

### Customers `/customers`
| Method | Endpoint | Description |
|---|---|---|
| POST | `/customers` | Add a customer |
| GET | `/customers` | List all customers |
| GET | `/customers/{id}` | Get customer by ID |
| PUT | `/customers/{id}` | Update customer |
| DELETE | `/customers/{id}` | Delete customer |
| GET | `/customers/{id}/warranties` | Get warranties for a customer |

### Sales `/sales`
| Method | Endpoint | Description |
|---|---|---|
| POST | `/sales` | Create a new sale (POS checkout) |
| GET | `/sales` | Sales history |
| GET | `/sales/{id}` | Get sale with line items |
| GET | `/sales/receipt/{receiptNumber}` | Fetch sale by receipt number |
| PATCH | `/sales/{id}/refund` | Mark sale as refunded |

### Quotations `/quotations`
| Method | Endpoint | Description |
|---|---|---|
| POST | `/quotations` | Create a quotation |
| GET | `/quotations` | List all quotations |
| GET | `/quotations/{id}` | Get quotation with items |
| PUT | `/quotations/{id}` | Update quotation |
| PATCH | `/quotations/{id}/convert` | Convert quotation to sale |
| DELETE | `/quotations/{id}` | Delete quotation |

### Purchases `/purchases`
| Method | Endpoint | Description |
|---|---|---|
| POST | `/purchases` | Create a purchase order |
| GET | `/purchases` | List all purchase orders |
| GET | `/purchases/{id}` | Get purchase order with items |
| PATCH | `/purchases/{id}/receive` | Mark as received (increments stock) |
| DELETE | `/purchases/{id}` | Cancel purchase order |

### Suppliers `/suppliers`
| Method | Endpoint | Description |
|---|---|---|
| POST | `/suppliers` | Add a supplier |
| GET | `/suppliers` | List all suppliers |
| GET | `/suppliers/{id}` | Get supplier by ID |
| PUT | `/suppliers/{id}` | Update supplier |
| DELETE | `/suppliers/{id}` | Delete supplier |

### Warranties `/warranties`
| Method | Endpoint | Description |
|---|---|---|
| POST | `/warranties` | Register a warranty |
| GET | `/warranties` | List all warranties |
| GET | `/warranties/{id}` | Get warranty by ID |
| PATCH | `/warranties/{id}/claim` | Mark warranty as claimed |
| GET | `/warranties/expiring` | Warranties expiring within 30 days |

### Gift Cards `/gift-cards`
| Method | Endpoint | Description |
|---|---|---|
| POST | `/gift-cards` | Create a gift card |
| GET | `/gift-cards` | List all gift cards |
| GET | `/gift-cards/{code}` | Fetch gift card by code |
| PATCH | `/gift-cards/{code}/redeem` | Redeem gift card (deducts balance) |

### Reports `/reports`
| Method | Endpoint | Description |
|---|---|---|
| GET | `/reports/daily` | Total sales and revenue for a given date |
| GET | `/reports/category` | Revenue breakdown by category |
| GET | `/reports/stock` | Stock valuation report |

---

## Database

**Engine:** PostgreSQL running in a Docker container

**Database name:** `electroprodb`

Tables are auto-generated by Hibernate on startup via `ddl-auto: update`.

### Entity Relationships

```
Category ────────── OneToMany ──→ Product
Customer ────────── OneToMany ──→ Sale
Customer ────────── OneToMany ──→ Warranty
Customer ────────── OneToMany ──→ Quotation
Sale ────────────── OneToMany ──→ SaleItem (cascade)
Supplier ────────── OneToMany ──→ PurchaseOrder
PurchaseOrder ───── OneToMany ──→ PurchaseOrderItem (cascade)
Quotation ───────── OneToMany ──→ QuotationItem (cascade)
SaleItem ────────── ManyToOne ──→ Product
PurchaseOrderItem ── ManyToOne ──→ Product
QuotationItem ───── ManyToOne ──→ Product
Warranty ────────── ManyToOne ──→ Product
```

---

## Getting Started

### Prerequisites

- Java 21+
- Maven
- Docker

### 1. Clone the repository

```bash
git clone https://github.com/jasper-nyambichu/electropos-spring.git
cd electropos-spring
```

### 2. Start PostgreSQL via Docker

```bash
docker run --name electropos-db \
  -e POSTGRES_USER=your_user \
  -e POSTGRES_PASSWORD=your_password \
  -e POSTGRES_DB=electroprodb \
  -p 5432:5432 \
  -d postgres
```

### 3. Configure `application.yml`

Update `src/main/resources/application.yml` with your credentials:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/electroprodb
    username: your_user
    password: your_password
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

server:
  port: 8080
```

### 4. Run the application

```bash
mvn spring-boot:run
```

Or run directly from IntelliJ IDEA using the green Run button.

The API will be available at `http://localhost:8080`.

---

## Frontend

This backend is consumed by the **ElectroPro POS** frontend built with Next.js (App Router), Tailwind CSS, and an offline-first architecture using Dexie.js (IndexedDB).

Frontend repo: _link to be added_

---

## Development Notes

- No Lombok — all getters, setters, and constructors are written explicitly
- DTOs are Java records
- Mappers are annotated with `@Service`
- Constructor injection is used throughout — no field injection
- Entities never cross the HTTP boundary; only DTOs are exposed

---

## Author

**Jasper Dickson Moseti**
Freelance Full-Stack Developer — Nairobi, Kenya
[GitHub](https://github.com/jasper-nyambichu)

---

## License

This project is proprietary software built for a private electronics retail client.
