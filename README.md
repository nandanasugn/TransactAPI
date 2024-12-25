# TransactAPI

TransactAPI is a Spring Boot project designed to facilitate transactional operations with comprehensive support for API documentation using Swagger.

---

## 🚀 Features

- **Redis** for caching.
- **PostgreSQL** as the relational database.
- **Swagger** for interactive API documentation.
- **JWT** for secure authentication.
- Multipart file upload support (up to 10MB per file).

---

## 🎯 Getting Started

### 📋 Prerequisites

Ensure you have the following installed:

- **Java 17**
- **PostgreSQL**
- **Redis**
- **Gradle**

Additionally, configure the required environment variables:

- `REDIS_HOST`
- `REDIS_PASSWORD`
- `REDIS_PORT`
- `DATASOURCE_URL`
- `DATASOURCE_USERNAME`
- `DATASOURCE_PASSWORD`
- `BASE_URL_STATIC_FOLDER`
- `STATIC_FOLDER_PATH`
- `JWT_SECRET`
- `JWT_EXPIRATION`

---

### ⚙️ Configuration

Below are the key configurations from the `application.properties` file:

```properties
spring.application.name=TransactAPI

server.address=0.0.0.0
server.port=8080

spring.data.redis.host=${REDIS_HOST}
spring.data.redis.password=${REDIS_PASSWORD}
spring.data.redis.port=${REDIS_PORT}

spring.datasource.url=${DATASOURCE_URL}
spring.datasource.username=${DATASOURCE_USERNAME}
spring.datasource.password=${DATASOURCE_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.jackson.time-zone=Asia/Jakarta

spring.jpa.hibernate.ddl-auto=update

spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=20MB

base.url.static.folder=${BASE_URL_STATIC_FOLDER}
static.folder.path=${STATIC_FOLDER_PATH}

spring.main.allow-circular-references=true

jwt.secret.key=${JWT_SECRET}
jwt.token.expiration=${JWT_EXPIRATION}
```

---

### 🏃‍♂️ Running the Application

1. Clone the repository:

   ```bash
   git clone <repository-url>
   cd TransactAPI
   ```

2. Build the project:

    - With **Gradle**:
      ```bash
      ./gradlew build
      ```

3. Run the application:

   ```bash
   java -jar build/libs/TransactAPI.jar
   ```

4. Open your browser and navigate to `http://localhost:8080`.

---

## 📘 API Documentation

Interact with the API using the Swagger UI:

- URL: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Explore and test endpoints directly through the interactive interface.

---

## 🤝 Contributing

Contributions are welcome! Here's how you can contribute:

1. Fork the repository.
2. Create a feature branch: `git checkout -b feature-name`.
3. Commit changes: `git commit -m 'Add feature'`.
4. Push the branch: `git push origin feature-name`.
5. Submit a pull request.

---

## 📜 License

This project is licensed under the MIT License. See the LICENSE file for details.

---

### ❤️ Support

If you like this project, don't forget to give it a ⭐ on GitHub!
