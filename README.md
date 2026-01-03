# Cucumber Automation Framework

A comprehensive BDD (Behavior-Driven Development) test automation framework built with Java, Cucumber, Selenium, RestAssured, and JDBC.

## 🏗️ Architecture

```
├── src/main/java/com/automation/
│   ├── api/              # API utilities (RestAssured)
│   ├── config/           # Configuration reader
│   ├── db/               # Database utilities (JDBC)
│   ├── pages/            # Page Object Model classes
│   └── utils/            # Common utilities (DriverManager, BrowserUtils)
│
├── src/test/java/com/automation/
│   ├── hooks/            # Cucumber hooks (Before/After)
│   ├── runners/          # Test runners (CukesRunner)
│   └── stepdefinitions/  # Step definition classes
│
└── src/test/resources/
    └── features/         # Cucumber feature files
```

## 🛠️ Tech Stack

| Component | Technology |
|-----------|------------|
| Build Tool | Maven |
| BDD Framework | Cucumber 7.x |
| Test Runner | JUnit 5 |
| UI Testing | Selenium WebDriver 4.x |
| API Testing | RestAssured 5.x |
| Database | JDBC (MySQL, PostgreSQL) |
| Reporting | Allure Reports |
| Logging | Log4j2 |

## ⚙️ Configuration

Edit `src/main/resources/config.properties`:

```properties
# Application URLs
base.url=https://your-app.com
api.base.url=https://api.your-app.com

# Browser settings
browser=chrome
headless=false

# Database
db.url=jdbc:mysql://localhost:3306/testdb
db.username=root
db.password=password
```

## 🚀 Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run by Tags
```bash
# Smoke tests
mvn test -Dcucumber.filter.tags="@smoke"

# Regression tests
mvn test -Dcucumber.filter.tags="@regression"

# UI tests only
mvn test -Dcucumber.filter.tags="@ui"

# API tests only
mvn test -Dcucumber.filter.tags="@api"

# Database tests only
mvn test -Dcucumber.filter.tags="@db"

# Combine tags
mvn test -Dcucumber.filter.tags="@smoke and @api"
mvn test -Dcucumber.filter.tags="@regression and not @wip"
```

### Run Using Profiles
```bash
mvn test -Psmoke
mvn test -Pregression
mvn test -Papi
mvn test -Pui
```

### Run Specific Feature
```bash
mvn test -Dcucumber.features="src/test/resources/features/login.feature"
```

### Re-run Failed Tests
```bash
mvn test -Dtest=FailedTestRunner
```

## 📊 Generating Reports

### Allure Report
```bash
# Generate and open report
mvn allure:serve

# Generate report only
mvn allure:report
```

### Cucumber HTML Report
Reports are automatically generated at `target/cucumber-reports/cucumber.html`

## 📝 Writing Tests

### Feature File Example
```gherkin
@ui @smoke
Feature: User Login
  
  Scenario: Successful login
    Given the user is on the login page
    When the user logs in with username "testuser" and password "pass123"
    Then the user should be redirected to the home page
```

### Step Definition
```java
@When("the user logs in with username {string} and password {string}")
public void userLogsIn(String username, String password) {
    loginPage.login(username, password);
}
```

### Page Object
```java
public class LoginPage extends BasePage {
    @FindBy(id = "username")
    private WebElement usernameInput;
    
    public void enterUsername(String username) {
        sendKeys(usernameInput, username);
    }
}
```

## 🏷️ Available Tags

| Tag | Description |
|-----|-------------|
| `@smoke` | Smoke tests |
| `@regression` | Regression tests |
| `@ui` | UI tests (Selenium) |
| `@api` | API tests (RestAssured) |
| `@db` | Database tests (JDBC) |
| `@wip` | Work in progress (excluded) |
| `@positive` | Positive test cases |
| `@negative` | Negative test cases |

## 📁 Project Structure Details

### Pages (Page Object Model)
- `BasePage.java` - Abstract base class with common methods
- `LoginPage.java` - Login page interactions
- `HomePage.java` - Home page interactions

### Utilities
- `DriverManager.java` - WebDriver lifecycle management
- `BrowserUtils.java` - Common browser operations
- `ApiUtils.java` - RestAssured wrapper methods
- `DatabaseUtils.java` - JDBC operations
- `ConfigReader.java` - Properties file reader

### Hooks
- `Hooks.java` - Setup/teardown logic, screenshot on failure
- `ScenarioContext.java` - Share data between steps

## 🔧 Extending the Framework

### Adding New Page Objects
1. Create class extending `BasePage`
2. Add `@FindBy` annotated elements
3. Implement page-specific methods

### Adding New API Endpoints
1. Use `ApiUtils` methods in step definitions
2. Store responses in `ScenarioContext`

### Adding Database Queries
1. Use `DatabaseUtils` methods
2. Store results in `ScenarioContext`

## 💡 Best Practices

1. **Page Objects**: Keep UI interactions in page classes
2. **Step Definitions**: Keep steps reusable and atomic
3. **Tags**: Use meaningful tags for test organization
4. **Data-Driven**: Use Scenario Outline with Examples
5. **Context**: Use ScenarioContext for step communication

## 📌 Dependencies

All dependencies are managed in `pom.xml`. Key versions:
- Selenium 4.16.1
- Cucumber 7.15.0
- RestAssured 5.4.0
- Allure 2.25.0
- JUnit 5.10.1

