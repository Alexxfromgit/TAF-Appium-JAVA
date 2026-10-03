# Changelog

All notable changes to this project are documented here. The format follows
[Keep a Changelog](https://keepachangelog.com/en/1.1.0/) and the project uses [Semantic Versioning](https://semver.org/).

## [Unreleased]

## [2.0.0] - 2026-10-03

First tagged release. It merges the separate approaches of the earlier code (config-driven tests, parallel runs from
TestNG parameters, Appium samples) into one architecture: `BaseTest` -> `DriverFactory` -> `DriverManager`.

### Added
- Layered configuration: `config.properties`, device profiles (`-Ddevice=`), environment variables, `-D` flags.
- Local or remote Appium: one server started per port, or a given URL (Grid, Sauce Labs via `sauce:options`).
- Parallel runs across devices with `suites/parallel.xml`.
- iOS support through `IOSDriver` and `XCUITestOptions` (`platform=ios`).
- Allure reports with `@Step` on page objects, and failure screenshots that include the device name.
- GitHub Actions: framework tests and a TestNG dry run of every device suite.
- `./gradlew downloadApiDemos` for the Appium ApiDemos samples.

### Changed
- Gradle 9.8 and a Java 21 toolchain (was Gradle 4.6, Java 8).
- Appium java-client 10.1 with Selenium 4 and W3C capabilities (`UiAutomator2Options`, `XCUITestOptions`) instead of
  java-client 6.1 and `DesiredCapabilities`.
- TestNG 7.12 and AssertJ; SLF4J with Logback instead of Log4j 1.x.
- Drivers are held per thread instead of in a static field.
- Explicit `Duration`-based waits (`Waits`, `BasePage`) instead of `Thread.sleep`.
- Web tests use the-internet practice site instead of the Facebook login page.

### Removed
- Bundled Selendroid jars and chromedriver binaries. UiAutomator2 downloads a matching Chromedriver itself.

[Unreleased]: https://github.com/Alexxfromgit/TAF-Appium-JAVA/compare/v2.0.0...HEAD
[2.0.0]: https://github.com/Alexxfromgit/TAF-Appium-JAVA/releases/tag/v2.0.0
