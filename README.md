# 🚀 Star Wars API Automation Suite

Automated testing framework developed to validate the **SWAPI (Star Wars API)** endpoints. This project demonstrates a custom-built automation engine using Java and JAX-RS, focusing on **Deep Packet Inspection (DPI)** via Regex, high-performance parsing and robust resource management.

---

## 🛠️ Tech Stack

* **Language:** Java 8+
* **HTTP Client:** JAX-RS / Jersey Client
* **Testing Framework:** JUnit 4
* **Logging:** Java Util Logging (JUL)
* **Build Tool:** Maven

---

## 🌟 Key Features & Engineering Highlights

### 1. Custom Regex-Based Parsing (DPI Style)
Instead of relying on heavy reflection-based JSON libraries (like Jackson or Gson), this framework uses a **Master Regex Engine**. This approach allows for:
* **Zero-overhead extraction:** Directly targeting fields in the raw response stream.
* **Type Agnostic:** Handles Strings, Integers and Booleans seamlessly through sophisticated regex patterns.
* **Data Sanitization:** Automated cleaning of JSON formatting characters ('[', ']', '"') to ensure data integrity during request chaining.

### 2. Robust Resource Management
Implemented a **Strict Lifecycle Control** for the HTTP Client using "try-finally" blocks. This ensures that every Jersey Client instance is explicitly destroyed after execution, preventing memory leaks in high-scale execution environments.

### 3. Advanced Test Scenarios
* **Data Correlation (Request Chaining):** Demonstrates the ability to extract dynamic data from one resource (e.g., a Character) and use it to drive subsequent validations (e.g., its linked Film).
* **HATEOAS & Pagination:** Validation of collection integrity and navigation metadata ("next" page links and "count" fields).
* **Negative Testing:** Custom exception handling ("InvalidHttpErrorCodeException") to validate 404 Not Found scenarios and error payload messages.

### 4. Enterprise-Grade Logging
Detailed audit logs for every execution step, providing forensic proof of assertions and data transformations.

---

## 📂 Project Structure

```text
starwars-api-automation
├── src/main/java
│   └── com.github.gcastelo22
│       ├── core          # Base engine and HTTP logic
│       ├── exceptions    # Custom business exceptions
│       └── utils         # Data sanitization and helpers
└── src/test/java
    └── com.github.gcastelo22
        └── tests         # Functional test suites (People, Starships)
```

## 🚀 How to Run

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/gcastelo22/starwars-api-automation.git](https://github.com/gcastelo22/starwars-api-automation.git)
    ```

2.  **Navigate to the project folder:**
    ```bash
    cd starwars-api-automation
    ```

3.  **Run the tests via Maven:**
    ```bash
    mvn test
    ```

> **Note:** Ensure you have **Java 8+** and **Maven** installed and configured in your system's PATH.

---

## 📄 License

Distributed under the **MIT License**. This permissive license allows for reuse and modification while providing the original author with proper credit. See the `LICENSE` file in the root directory for the full legal text.

---

**Developed by Guilherme Castelo**
*Senior Quality Engineer | SDET | Data Integrity Specialist*

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/guilhermecastelo/)
[![Email](https://img.shields.io/badge/Email-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:lguilherme.castelo@gmail.com)	