
# Event Ledger API

Spring Boot REST API for handling financial transaction events with:
- Idempotency
- Out-of-order event handling
- Balance computation
- Validation
- Automated tests

## Tech Stack

- Java 17
- Spring Boot 3
- PostgreSQL
- Maven
- JUnit 5

## Prerequisites

- Java 17
- Maven 3.9+
- PostgreSQL

## PostgreSQL Setup

Create database:

```sql
CREATE DATABASE eventledger;
```

Update credentials in:
`src/main/resources/application.yml`

## Run Application

```bash
mvn spring-boot:run
```

## Run Tests

```bash
mvn test
```

## APIs

### Submit Event

POST /events

### Get Event

GET /events/{id}

### Get Events By Account

GET /events?accountId=acct-123

### Get Account Events

GET /accounts/{accountId}

### Get Balance

GET /accounts/{accountId}/balance
