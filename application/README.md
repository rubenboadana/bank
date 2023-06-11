### Main module

The aim of this module is to be the glue of the other domain modules. Here you will find:

1. pom.xml: Both modules (user, wallet) are direct dependencies of this module, in order to include all their
   functionalities
2. Dockerfile: Builds the Docker image which contains our Spring Boot Rest API. Layered build to cache the parts that
   doesn't change often and thus decrease the build time.
3. docker-compose.yml: Defines the infrastructure required to run the application: app, db and command/query/event bus,
   as well as the shared network
4. docker-compose-debug.yml: Same as before but with debug exposed ports (and Docker app entrypoint overwritten).
4. e2e tests (src/test/java): Cucumber/Gherkin + Testcontainers \
   Given that the e2e tests are testing the entire flow point to point: HTTP request -> security -> controller ->
   command/query bus ->
   handlers/service -> persistence (PostgreSQL) \
   they really require about the entire monolith application running, and therefore shouldn't be placed in any specific
   module/domain.