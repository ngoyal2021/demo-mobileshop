# Mobile Automation Test Framework Documentation

## 1. Architecture Overview

This project implements a **layered Espresso-based mobile automation framework** designed with maintainability, scalability, and CI compatibility in mind.

### Design Patterns Used

- **Page Object Model (POM)**  
  Each screen in the app is represented by a dedicated Page class under `androidTest/pages` (e.g., `ProductListPage`, `CartPage`). Page classes encapsulate UI interactions and assertions, keeping test logic clean and readable.

- **Template Method Pattern**  
  `BaseTest` defines the common test lifecycle (setup, teardown, configuration loading), while concrete test classes extend it.

- **Factory / Provider Pattern**  
  `TestConfig` and environment-aware logic decide runtime behavior (mock vs real backend, device config, etc.).

- **Rule-based Test Control**  
  `MockServerRule` can skip or run tests enabled only for mock server (like error scenarios)

- **Separation of Concerns**
    - Test orchestration → `tests`
    - UI interactions → `pages`
    - Configuration & lifecycle → `common`
    - Assertions & helpers → `utils`

---

## 2. Tech Stack

### Languages
- **Kotlin** – Primary language for test framework and test cases
- **Java** – Application under test

### Automation & Testing Tools
- **Espresso** – Core UI automation framework
- **UIAutomator** – To be used where cross-app or system-level interactions are required (Future implementation)
- **JUnit4** – Test runner and lifecycle management

### Build & Dependency Management
- **Gradle (Kotlin DSL)** – Project and test build system

### Mocking & Test Isolation
- **MockWebServer (custom wrapper)** – Controlled via `MockServerRule`, using okhttp mockwebserver

### CI / Cloud Execution
- **Sauce Labs** – Configured via `.sauce/config.yml`
- **Fastlane** – Used for orchestration and CI execution

### Reporting
- **JUnit XML reports**
- ** Generated for CI consumption ** (`fastlane/report.xml`)

---

## 3. Core Components

### Key Directories

```
androidTest/
 ├── annotations/      # Custom test annotations
 ├── common/           # BaseTest, configuration, mock rules
 ├── pages/            # Page Object classes
 ├── tests/            # Test scenarios
 └── utils/            # Assertion helpers & extensions
```

### Important Classes

- **BaseTest**  
  Central test superclass responsible for:
    - Test environment setup
    - Mock server initialization
    - Shared configuration access

- **MockServerRule**  
  JUnit Rule that starts/stops mock backend conditionally based on annotations.

- **TestConfig**  
  Centralized configuration provider (mocked vs real data, environment flags).

- **AssertionExtension**  
  Custom assertion wrappers to improve readability and failure diagnostics.

---

## 4. Locator Strategy

### Locator Types Used

- **Resource IDs**
- **Text matchers**

---

## 5. Data Management

### Test Data Sources

- **JSON Assets**  
  `mock_products.json` under `src/main/assets` is used for deterministic test data when mock server is enabled.

- **Hardcoded Scenarios**  
  Some flow validations are embedded directly in tests for clarity.

## 6. Principles
- Espresso-first
- UIAutomator as escape hatch (future implementation)
- No business logic in tests
- CI-ready by default

### Future implementation Plan

- Externalize complex scenarios into JSON fixtures
- Introduce a Test Data Builder for complex objects

---