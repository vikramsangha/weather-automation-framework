# Weather Automation Framework

A production‑grade API automation framework for validating weather services across **live (PROD)** and **fixture‑based (AQA)** environments. The framework demonstrates clean separation of concerns, contract‑driven client design, environment‑aware execution, and scalable test orchestration using modern Java tooling.

---

## 🚀 Key Capabilities

- **Dual‑mode execution**
  - **PROD** → Executes tests against live public APIs
  - **AQA** → Executes tests fully offline using JSON fixtures

- **Contract‑driven client**
  - OpenAPI‑generated client wrapped by a stable, test‑friendly core layer
  - No direct OpenAPI or HTTP noise inside tests

- **Authentication architecture (OAuth‑ready)**
  - Token model, provider, caching, and expiry handling
  - Mocked auth server for offline execution

- **Tag‑based execution**
  - Run any subset of tests using JUnit 5 tags (e.g. smoke, weather_check, temperature_check)

- **Clear test intent**
  - Expressive test names and display names
  - Environment awareness logged at runtime

- **Reporting & observability**
  - Integrated **Allure** reporting
  - Structured logs for environment and execution flow

---

## 🧱 Project Structure

```
weather-automation-framework
│
├── openapi-client
│   └── Generated OpenAPI client (never touched manually)
│
├── core
│   ├── WeatherClient / WeatherClients
│   ├── WeatherConfig
│   ├── AuthClient, TokenProvider, OAuthInterceptor
│   └── Domain‑level abstractions
│
├── tests
│   ├── PROD tests (live API)
│   ├── AQA tests (fixtures)
│   ├── WireMock setup for auth mocking
│   └── Test configuration & reporting
│
└── pom.xml
```

---

## 🧪 Test Execution

### Run against live APIs (PROD)

```bash
mvn clean verify -PPROD -Dtest.tags=weather_check
```

### Run fully offline using fixtures (AQA)

```bash
mvn clean verify -PAQA -Dtest.tags=weather_check
```

### Example tags

- `weather_check`
- `temperature_check`
- `smoke`

Tags are **not hard‑coded in Maven** — any new tag can be added at test level and executed dynamically.

---

## 📊 Allure Reporting

After test execution:

```bash
allure serve tests/target/allure-results
```

Reports include:
- Environment (PROD / AQA)
- Test display names
- Steps, parameters, and failures

---

## 🔐 Authentication Design

- OAuth token retrieval via pluggable `AuthClient`
- Token caching with expiry handling
- WireMock‑backed auth server for offline runs
- Transparent injection into HTTP client via interceptor

This design allows the same test suite to run unchanged across secured and unsecured environments.

---

## 🧠 Design Principles

- Tests validate **behavior**, not transport details
- Generated code is isolated and replaceable
- Environments are selected externally, never hard‑coded
- Abstractions are introduced only after contracts are proven

---

## 🛠 Tech Stack

- Java 21
- Maven
- JUnit 5
- AssertJ
- OpenAPI Generator
- WireMock
- Allure

---

## 📌 Notes

- The framework is CI‑ready and OS‑agnostic
- Designed to scale toward richer domain‑specific builders
- Suitable as a foundation for enterprise API automation

---

**Author:** Vikram Sangha

