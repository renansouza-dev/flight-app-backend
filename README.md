# Flight Data Management Application
RESTful APIs to manage flight data
## Decision makings
##### Use of context path
To implement this structure, I define the context path, such as /api/v1, in my application.yml configuration file. This path serves as the foundation for all API endpoints. For example:
```yaml
server:
  servlet:
    context-path: /api/v1
```

##### Use of Java 21 and Spring Boot 3
To have the latest technology stack, I chose to use Java 21 and Spring Boot 3. This may aid in the migration to newer LTS versions in the future, or at the very least, a longer deadline after the end of life for Java 21 support.

#### Monitoring
From many possible solutions, I choose to add only actuator and Prometheus. As an MVP, I think this is enough to start monitoring and collecting data for decision-making.

#### GitHub Actions
GitHub Actions provide a wide range of plugins to help CI and CD pipelines, increasing productivity, efficiency and reducing errors. I choose to use the workflow that only run the tests before merging to main. But I also have experience with more workflows, such as build docker image, add coverage badge to the README, plan terraform IaC, deploy to AWS.

#### Use of Java Records and Projections
The use of Java Records and Spring Projections, were used to showcase my commitment to self learning. With Java Records, the best benefit is the immutability and readability improvement without the Lombok dependency. As for Spring Projections, the benefits rely on the mapping of an entity to a Java Record without the need for extra code.

## Possible Improvements
My cherry-pick possible improvements choices would be turning the crazy-flight supplier a new project, decoupling from flights backend app. If more functionalities were added or the need of scale became critical, I therefore would focus on refactoring for a microservice approach.


## There are four operations possible
<details>
  <summary>Get</summary>

> The get method may receive filter and sorting params through request parameters.

[GET /api/v1/flights HTTP/1.1](http://localhost:8080/api/v1/swagger-ui/index.html#/flights/getFlights)
```json
{
  "content": [],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "offset": 0,
    "unpaged": false,
    "paged": true
  },
  "last": true,
  "totalPages": 0,
  "totalElements": 0,
  "size": 20,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "numberOfElements": 0,
  "first": true,
  "empty": true
}
```

> **🚧** Sorting and filtering
> Requesting the root path of flights will add `pageSize=20`, `direction=asc` and `property=airline`
> as default request parameters.
</details>
<details>
  <summary>Create</summary>

> The creation receives a payload through a patch method.

[POST /api/v1/flights/ HTTP/1.1](http://localhost:8080/api/v1/swagger-ui/index.html#/flights-controller/createFlight)
```json
{
  "airline": "AVIOS Airlines",
  "supplier": "avios",
  "fare": 1.0,
  "departureAirport": "OPO",
  "arrivalAirport": "AMS",
  "departureTime": "2024-01-01T12:30:45.000Z",
  "arrivalTime": "2024-01-01T12:30:45.000Z"
}
```

> **🚧** Constrains
> For the development of this API the timezone was not considered,
> which means that total flight time will be accurate.
</details>
<details>
  <summary>Update</summary>

> The update receives a payload through a patch method.

[PATCH /api/v1/flights/ HTTP/1.1](http://localhost:8080/api/v1/swagger-ui/index.html#/flights-controller/updateCompanies)
```json
{
  "id": "3408e925-3b24-43ab-b589-1cf52374f30d",
  "airline": "AVIOS Airlines",
  "supplier": "avios",
  "fare": 1.0,
  "departureAirport": "OPO",
  "arrivalAirport": "AMS",
  "departureTime": "2024-01-01T12:30:00.000Z",
  "arrivalTime": "2024-01-01T14:30:00.000Z"
}
```
</details>
<details>
  <summary>Delete</summary>

> The delete method make use of soft delete strategy

[DELETE /api/v1/flights/{id} HTTP/1.1](http://localhost:8080/api/v1/swagger-ui/index.html#/flights-controller/deleteFlight)
```json
{
  "id": "3408e925-3b24-43ab-b589-1cf52374f30d"
}
```
</details>
