# AI-Powered Weather Forecast and Analytics Platform

Spring Boot 3 portfolio project for weather forecasting, analytics dashboards, role-based authentication, PostgreSQL persistence, OpenWeather integration, and Spring AI powered recommendations.

## Tech Stack

Java 21, Spring Boot 3, Spring MVC, Spring Security, Spring Data JPA, Hibernate, PostgreSQL, Thymeleaf, Bootstrap 5, JavaScript, Chart.js, Spring AI, OpenAI API, OpenWeather API, Maven, Docker.

## Features

- User registration, login/logout, BCrypt passwords, CSRF protection, session management, USER/ADMIN roles.
- Current weather dashboard with temperature, feels-like, humidity, pressure, visibility, wind, sunrise, sunset, and weather icons.
- 24-hour forecast, 7-day forecast projection, city search, search history persistence.
- AI weather summary, travel recommendation, clothing advice, and health tips.
- Chart.js analytics for temperature, humidity, rainfall-ready data, and wind speed.
- Admin dashboard with users, searches, and stored weather snapshot counts.
- PostgreSQL schema for users, roles, cities, weather data, favorite cities, search history, weather alerts, and audit logs.
- Docker and Docker Compose support.

## Quick Start

```bash
cp .env.example .env
docker compose up --build
```

Open `http://localhost:8080`.

Default admin:

- Email: `admin@weather.local`
- Password: `Admin@12345`

Set `OPENWEATHER_API_KEY` for live integrations. Without an OpenWeather key, the app uses demo weather data so the UI remains usable.

## Local Development

```bash
./mvnw test
./mvnw spring-boot:run
```

For a no-PostgreSQL local demo, run the in-memory dev profile:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

## Architecture

```text
src/main/java/com/weather
  client        OpenWeather API client
  config        external API and seed-data configuration
  controller    MVC controllers
  dto           request/response DTOs
  entity        JPA entities
  exception     global exception handling
  mapper        DTO mappers
  repository    Spring Data repositories
  security      Spring Security integration
  service       business interfaces and implementations
```

## Key Routes

- `GET /` landing page
- `GET /register`, `POST /register`
- `GET /login`, `POST /login`
- `GET /dashboard?city=London`
- `GET /weather`, `POST /weather/search`
- `GET /admin`

## System Design

- MVC controllers render Thymeleaf views.
- Services own business rules and external API orchestration.
- Repositories isolate persistence access.
- DTOs keep web-layer models separate from entities.
- PostgreSQL stores users, roles, city data, searches, favorites, alerts, logs, and analytics snapshots.

## Documentation

- [Development Roadmap](docs/ROADMAP.md)
- [Database ER Diagram](docs/ER_DIAGRAM.md)
- [API Documentation](docs/API.md)

## Resume Description

Built an AI-powered weather forecasting and analytics platform using Java 21, Spring Boot 3, Spring Security, PostgreSQL, Thymeleaf, Chart.js, Docker, OpenWeather API, and Spring AI. Implemented secure authentication, role-based access control, weather dashboards, forecast analytics, search history persistence, AI-generated travel/clothing/health recommendations, admin statistics, and Dockerized deployment.

## Production Checklist

- Replace demo API mode with required `OPENWEATHER_API_KEY`.
- Use managed PostgreSQL and rotate credentials through secret storage.
- Add Flyway/Liquibase migrations before team development.
- Add email verification and forgot-password mail delivery.
- Add Testcontainers integration tests.
- Enable HTTPS, actuator health checks, and structured log shipping.
