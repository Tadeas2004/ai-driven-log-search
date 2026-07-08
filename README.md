# AI-Driven Audit Log Search 🔍🤖

A proof-of-concept Spring Boot application demonstrating how to seamlessly convert natural language queries into structured SQL database filters using **Spring AI** and Large Language Models (LLMs).

Instead of forcing users to navigate complex filtering forms, this application allows them to search database logs intuitively.

## 🚀 Core Architecture (How it works)

This system prevents typical LLM "hallucinations" and protects the database from SQL injections by strictly enforcing a structured output. The data flows through 5 main steps:

1. **Capture:** The frontend sends a natural language prompt via a REST API to the Java backend.
2. **Forwarding:** The backend forwards the text to the LLM via Spring AI.
3. **Translation:** The AI is provided with a strict JSON schema. It is forced to perform entity extraction and return a clean, structured JSON response rather than conversational text.
4. **Mapping:** The JSON payload is automatically deserialized into a strongly typed Java Record (`AuditFilterRequest`).
5. **Execution:** The populated Java object is passed to the database layer (JPA), which dynamically constructs a safe and optimized SQL query.

## 🛠️ Tech Stack

* **Backend:** Java 25Backend: Java 25 (LTS), Spring Boot 3.x
* **AI Integration:** Spring AI, Google Gemini 2.5 Flash
* **Database:** PostgreSQL, Spring Data JPA / Hibernate
* **API Documentation:** Swagger UI / OpenAPI

## ⚙️ Setup & Installation

### 1. Prerequisites
* Java 25 installed (`mvnw` is included in the project)
* Docker Desktop (for running the PostgreSQL instance)
* Google Gemini API Key

### 2. Infrastructure (Database)
Start your local PostgreSQL instance using Docker. The application expects the database to be running on `localhost:5432` with the database name `dirx_audit` (can be configured in `application.properties`).

### 3. Environment Variables
Create a `.env` file in the root directory of the project and add your AI provider API key:
```env
GEMINI_API_KEY=your_actual_api_key_here