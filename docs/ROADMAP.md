# Development Roadmap

## Phase 1: Project Foundation
- Objective: Java 21, Spring Boot 3, Maven, PostgreSQL, Thymeleaf, Security, Docker.
- Folders: `config`, `controller`, `dto`, `entity`, `repository`, `service`, `security`, `exception`, `client`.
- Best practices: environment variables, BCrypt, layered architecture, DTOs, validation, global exception handling.

## Phase 2: Authentication and Users
- Entities: `User`, `Role`.
- DTOs: `RegisterRequestDto`, `LoginRequestDto`, `UserResponseDto`.
- Services: registration, login support through Spring Security, profile management.
- Next production tasks: email verification mail sender, password reset tokens with expiry, account lockout.

## Phase 3: Weather Integration
- Client: `OpenWeatherClient`.
- Services: current weather, 24-hour forecast, 7-day forecast projection, search persistence.
- Pages: `/dashboard`, `/weather`.
- Next production tasks: cache API responses, add OpenWeather One Call 3.0 for UV index and alerts.

## Phase 4: AI Insights
- Service: `AIWeatherService`.
- Features: summary, travel, clothing, health advice, natural-language explanation.
- Next production tasks: structured JSON AI output, prompt versioning, AI usage audit table.

## Phase 5: User Productivity
- Entities: `FavoriteCity`, `SearchHistory`.
- Features: favorites, history, dashboard personalization.
- Next production tasks: favorite city widgets, preferred units, preferred theme.

## Phase 6: Alerts and Notifications
- Entity: `WeatherAlert`.
- Features: heavy rain, storm, flood, heatwave alerts.
- Next production tasks: scheduled alert scanner, email/SMS notifications, severity rules.

## Phase 7: Analytics
- Entity: `WeatherData`.
- Features: temperature, humidity, rainfall, wind charts with Chart.js.
- Next production tasks: daily aggregates, admin API usage dashboards, export CSV.

## Phase 8: Admin and Observability
- Entities: `AuditLog`, users, searches, weather snapshots.
- Features: user count, search count, API usage-ready structure.
- Next production tasks: actuator, structured JSON logs, admin user management screens.

## Phase 9: Testing
- Unit tests: services and mappers.
- MVC tests: auth and dashboard routes.
- Integration tests: repositories with H2/Testcontainers.
- Security tests: role access and CSRF behavior.

## Phase 10: Deployment
- Local: `docker compose up --build`.
- Production: set environment variables, use managed PostgreSQL, configure HTTPS at reverse proxy.
- CI/CD: run `./mvnw test`, build Docker image, deploy with secrets from the platform.
