# HOW TO RUN

## Quick start with Docker

1. In the CLI navigate to root folder of this project (where the file `docker-compose.yml` is located)
2. Run `mvn clean package -DskipTests`
2. Run `docker-compose up`
3. Open the following URL in the browser: http://localhost:8080/api/v1/swagger-ui/index.html#/

## Run unit tests
- Backend: Run command `mvn clean test`

## Run via IDE (IntelliJ IDEA)

In case it is necessary to run with the IDE, make sure the following requirements are met:

- Database: Run a PosgreSQL database on port 5432 and create an empty database `flight-app-db` with owning user `flightuser` (password `flightpass`)
- Backend: Run a Spring Boot project `mvn spring-boot:run`.
- Crazy flight Supplier: if the test API for this service is down or failing, a mockserver can be run (https://www.mock-server.com/mock_server/running_mock_server.html) using the expectations file from the `crazy-flight-mock` folder. The `CRAZY_FLIGHT_BASEURI` environment variable must point to the running mockserver and passed to the Backend.
