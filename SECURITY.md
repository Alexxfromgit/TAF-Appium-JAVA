# Security policy

## Reporting a vulnerability

Please **do not open a public issue** for security problems. Use GitHub's
[private vulnerability reporting](https://github.com/Alexxfromgit/TAF-Appium-JAVA/security/advisories/new) instead.
You will get a response within a few days.

## Credentials in tests

- Cloud credentials (`SAUCE_USERNAME`, `SAUCE_ACCESS_KEY`) are read from environment variables. Never put them
  into `config.properties` or a device profile.
- The login used by the web tests (`tomsmith` / `SuperSecretPassword!`) is the public demo account of
  [the-internet](https://the-internet.herokuapp.com), not a real secret.

If you find a credential committed to this repository, please report it as above.
