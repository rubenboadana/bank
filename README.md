# bank

[![CI pipeline status](https://github.com/rubenboadana/bank/actions/workflows/maven.yml/badge.svg)](https://github.com/rubenboadana/bank/actions)
![Coverage](.github/badges/jacoco.svg)
# 🐳 Required tools

1. Docker
2. Maven 3
3. JDK 17

# 👩‍💻🧾 How To Start

1. Build the application: `mvn clean install`
2. Start up Docker compose: `docker-compose up`
3. Go to browser and check the REST api documentation: http://localhost:8000/swagger-ui/index.html

# 🐛 How To debug

## IntelliJ

1. Choose the Run Configuration "Debug Bank application" and click 🐛 icon
   It will run the docker-compose-debug configuration firstly and attach to the process at port 5005 afterward

# 🤖🧾 GitHub actions

1. Commit and push your changes
2. Automatically a new workflow execution is triggered