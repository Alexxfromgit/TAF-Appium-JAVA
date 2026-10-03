# Contributing

Bug reports, docs fixes and features are all welcome.

## Build

```bash
./gradlew test                                              # framework tests, no device needed
./gradlew test -Psuite=mentoring -Dtestng.mode.dryrun=true  # check a device suite without a device
./gradlew test -Psuite=mentoring                            # run it on an emulator or device (Appium required)
```

JDK 21 must be installed (the Gradle toolchain picks it up). CI uses Temurin 21.

## Guidelines

- **Framework code goes into `src/main/java/com/apj/framework`** and must not depend on a specific app under test.
  Tests and page objects for the example apps live in `src/test`.
- Drivers are held per thread, so keep page objects and helpers free of static driver references.
- Wait for elements through `BasePage` and `Waits`, not `Thread.sleep`.
- New configuration keys need a default in `config.properties` and a row in the README's configuration reference.
- Never commit credentials, internal hostnames or real personal data. Cloud keys go into environment variables.
- Commit messages: imperative mood, short subject line (`Add iOS device profile`).

## Pull requests

1. Open an issue first for larger changes, so we can agree on the approach.
2. Keep PRs focused, and update `CHANGELOG.md` under *Unreleased*.
3. Make sure `./gradlew test` passes and that every suite still passes the dry run (CI checks both).

By contributing you agree that your contributions are licensed under the MIT License.
