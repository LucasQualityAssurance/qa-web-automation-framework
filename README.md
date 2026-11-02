# qa-web-automation-framework

Web UI test automation framework built with **Java 21**, **Selenium 4** and **JUnit 5**, following the
**Page Object Model**. The system under test is [the-internet](https://the-internet.herokuapp.com)
([source](https://github.com/saucelabs/the-internet)).

## Tech stack

| Tool | Purpose |
|---|---|
| Java 21 | Language |
| Selenium 4 | Browser automation (driver resolved automatically by Selenium Manager) |
| JUnit 5 | Test runner, including parameterized tests |
| Maven | Build and dependency management |

## Project structure

```
selenium-junit/
└── src
    ├── main/java
    │   ├── factory/DriverFactory.java   # Chrome setup (downloads folder, headless mode)
    │   ├── pages/                       # Page objects (BasePage + one class per page)
    │   └── utils/                       # ConfigReader, TestLogger, ArquivoUtils
    └── test/java
        ├── base/BaseTest.java           # Opens and closes the browser for every test
        ├── dados/                       # Shared test data (login credentials)
        └── tests/                       # Test classes
```

## Test scenarios

| Test class | Scenario |
|---|---|
| `LoginTest` | Logs in with valid credentials and returns to the home page |
| `LoginCredenciaisInvalidasTest` | Parameterized: blank password, blank username, invalid username and password |
| `CadastroFormularioTest` | Fills in every field of the Large Form and submits it |
| `DownloadUploadArquivoTest` | Downloads a file from the site into the project and uploads that same file back |

Test names, step logs and page object methods are written in Portuguese; assertions compare the
site's original (English) texts.

## How to run

Requirements: JDK 21, Maven 3.9+ and Google Chrome.

```bash
cd selenium-junit

# Run every test, opening the browser
mvn test

# Run without opening the browser window (e.g. in CI)
mvn test -Dheadless=true

# Run a single test class
mvn test -Dtest=LoginTest
```

Each test prints its steps to the terminal with the `[STEP]` prefix.

### Downloaded files

Chrome saves downloads into the `Files/` folder at the repository root. The folder is cleaned before
the download test runs and is ignored by Git. It can be changed with `-DdiretorioDownload=<path>`.
