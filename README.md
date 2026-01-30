
# README.md

## Project Title
**Demo Mobile Shop – Android Test Automation Framework**

## Description
This repository contains a demo Android UI automation framework built on Espresso. It supports both local and cloud execution (Sauce Labs), selective backend mocking, and CI-friendly reporting.

---

## Prerequisites

- Java 17+
- Android Studio (latest stable)
- Android SDK & Emulator
- Gradle
- Ruby (for Fastlane)
- Sauce Labs account (optional for cloud runs)

---

## Local Setup



## Purpose
Ensure local machines are correctly configured before running Android UI tests.

## 1. Java & Android SDK

```bash
java -version
sdkmanager --list
```

Required:
- Java 17+
- Android SDK Platform (API level matching app)
- Build-tools

Environment variables:
```bash
ANDROID_HOME=<path-to-sdk>
PATH=$PATH:$ANDROID_HOME/platform-tools
```

---

## 2. ADB Sanity Checks

```bash
adb version
adb devices
```

Expected:
- `adb` resolves correctly
- At least one device/emulator in `device` state

Restart if needed:
```bash
adb kill-server
adb start-server
```

---

## 3. Emulator Checks

```bash
emulator -list-avds
emulator @Pixel_8_API_36
```

Verify:
- Emulator boots fully
- No system popups blocking UI

---

## 4. Instrumentation Verification

```bash
adb shell pm list instrumentation
```

Expected:
- androidx.test.runner.AndroidJUnitRunner present

```bash
# Clone repository
git clone <repo-url>
cd demo-mobileshop

# Install Ruby dependencies
bundle install

# Sync Gradle dependencies
./gradlew clean build
```

## 5. Common Failures

| Issue | Fix |
|-----|----|
| adb not found | Fix PATH |
| No devices | Start emulator |
| Tests hang | Disable animations |

## 6. Multiple available devices:
Choose one via setting Android Serial

$env:ANDROID_SERIAL

---

## Configuration

### Mock vs Real Backend

Controlled via `TestConfig` and test annotations.

Gradle property :
useMockServer : default false


```kotlin
@RequiresMockServer
fun testServerErrorDisplaysErrorMessage() {}
```

### Sauce Labs Configuration

Edit `.sauce/config.yml`:

- App path
- Device name
- Platform version



---

## Running Tests

### Local Emulator

Run all tests:

```bash
./gradlew connectedAndroidTest
```

Run with mocks: 

```bash
./gradlew connectedAndroidTest "-PuseMockServer=true"
```

### Annotation based run

```bash
./gradlew connectedAndroidTest "-Pandroid.testInstrumentationRunnerArguments.annotation=com.example.shopmobile.annotations.SmokeTest"
```

### Sauce Labs Execution

## Configure Sauce user id and password

```bash
$env:SAUCE_USERNAME=<your sauce id>
$env:SAUCE_ACCESS_KEY=<your sauce password>
```

```bash
saucectl run
```

Or via Fastlane:

```bash
bundle exec fastlane sauce 
```

Fastlane commands with option:
```bash
bundle exec fastlane sauce mock:true annotation:SmokeTest
```

---

## Reporting

- JUnit XML reports
- Sauce Labs dashboards

---

## CI/CD Integration

✔ Sauce Labs (`.sauce/config.yml`)

✔ Fastlane pipelines (`fastlane/Fastfile`)


---

## Future Improvements

- Extent report integration
- Parallel device execution
- iOS automation parity

---










