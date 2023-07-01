# Java/SpringBoot3 modulith with Hexagonal architecture + CQRS + pub/sub communication

[![CI pipeline status](https://github.com/rubenboadana/bank/actions/workflows/maven.yml/badge.svg)](https://github.com/rubenboadana/bank/actions)
![Coverage](.github/badges/jacoco.svg)

# 🐳 Required tools

1. Docker
2. Maven 3
3. JDK 17
4. (Optional) IntelliJ as preferred IDE for out of the box run configurations

# 🧰 Tech Stack

* **Language**: Java 17
* **Framework**: Spring Boot 3.0.7
* **Testing**: JUnit Jupiter, MockMVC, TestContainers, Cucumber
* **Build tool**: Maven
* **API Doc**: Swagger
* **Containers**: Docker, Docker compose
* **Libraries**: Lombok
* **Authentication**: JWT
* **Database**: PostgreSQL
* **CI**: GitHub Actions
    * Build
    * Code coverage report
    * OWASP Dependencies checker
* **Architecture**: Modulith with Hexagonal architecture + CQRS + pub/sub events with Axon Server\
  The aim of this logical separation by feature/domain is keep delivering the application as a monolith due its
  simplicity but let us promote those modules which could require scalability in human/resource terms in an easy way.

# 👩‍💻🧾 How To Start

1. Build the application: `mvn clean install`
2. Start up Docker compose: \
   `cd application` \
   `docker-compose up` \
   **IntelliJ**: Choose the Run Configuration "Docker compose up" and click the ▶️ icon
3. Go to browser and check the REST api documentation: http://localhost:8000/swagger-ui/index.html
4. Track the commands, queries and events that are being sent across the system: http://localhost:8024/

# 🐛 How To debug

### IntelliJ

1. Choose the Run Configuration "Debug Bank application" and click the 🐛 icon \
   It will run the _docker-compose-debug_ configuration firstly and attach to the process at port 5005 afterward

# 🤖🧾 GitHub actions

1. Commit and push your changes
2. Automatically a new workflow execution is triggered
