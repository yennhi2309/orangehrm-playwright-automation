# OrangeHRM Employee Lifecycle Automation

**End-to-End Automation Testing Project** for Employee Management workflow in OrangeHRM using **Playwright with Java**.

---

## 📋 Scope

This project automates the complete employee lifecycle flow:

1. Admin logs in and creates a new employee with login credentials
2. Employee logs into the system using new account
3. Employee updates Personal Details
4. Employee updates Contact Details
5. Admin logs back in and verifies updated information
6. Admin deletes the employee
7. Verify deleted employee cannot log in

---

## ✅ Test Coverage

### Employee Management
- Create new employee
- Search employee by Employee ID
- Edit employee information (Personal & Contact Details)
- Delete employee
- Verify data persistence after update

### Validation Testing
- Required field validation
- Success / Error message validation
- Search and filter validation
- Invalid login attempt after deletion
- Avatar upload validation

---

## 🛠️ Tech Stack

- **Language**: Java 17+
- **Automation Tool**: Playwright
- **Test Framework**: TestNG
- **Build Tool**: Maven
- **Design Pattern**: Page Object Model (POM)
- **Reporting**: Allure Report
- **Logging**: SLF4J + Logback
- **IDE**: IntelliJ IDEA

---

## 🚀 Prerequisites

- Java 17 or higher
- Maven 3.8+
- Google Chrome / Microsoft Edge
- IntelliJ IDEA (Recommended)
- OrangeHRM application URL (Demo or Live)

---
## ⚙️ Installation & Setup

Clone the repository:

    git clone https://github.com/yennhi2309/orangehrm-playwright-automation.git  
    cd orangehrm-playwright-automation
    
Install dependencies:

    mvn clean install
    
Update configuration in src/test/resources/config/config.properties


---
## ▶️ Running Tests
Run all tests

    mvn test
Run specific test class

    mvn test -Dtest=UserLifeCycleTest    
Run with Allure Report

    mvn test allure:serve
    
---
## 📊 Reporting

    allure serve allure-results
    
---
## ✨ Key Features

- Dynamic locator strategy (anti-flaky)
- Reusable utilities (Table handling, File upload, Screenshot helper)
- Data-driven testing using EmployeeData
- Parallel test execution support
- Automatic screenshot on key steps
- Clean and maintainable code following POM pattern

---
## 🔧 Troubleshooting

- Browser not found: Run mvn playwright:install
- Allure report issue: Check Allure plugin in pom.xml
- Configuration: Verify config.properties file


---
## 📌 Notes

- Currently using OrangeHRM Open Source Demo.
- Test data is managed in tests.data.EmployeeData.
- It is recommended to use environment variables for credentials in production environments.
