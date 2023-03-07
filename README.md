# autoservice

**This is a RESTful app built using Spring Boot. It can process orders and favours of a car owner**

#### Functionality:
- creating and storing new entities - cars, owners, workers, favors, etc...
- updating any data any changing statuses
- calculating costs of orderings
- calculating salary of workers

#### Structure of the project:
- Repository layer - persistence layer that communicates with DB
- Service layer - business logic
- Controllers - accept requests and send responses

#### Technologies used in the project:
- Spring Boot 3
- Java 17
- Postgres 15.1
- Hibernate 6.1.6
- Liquibase 4.19.1
- Swagger 4.15.5
- Docker
- Lombok
- Maven
- JUnit 5.9.2
- Mockito 4.8.1

#### Instructions:
- download the project
- install and/or run Docker desktop
- run in project folder using terminal: `mvn clean package -DskipTests`
- run: `docker-compose up`
- open [swagger UI](http://localhost:6868/swagger-ui/index.html#/) to communicate with an API

#### List of available end-points:

POST: /cars - creates a new car entity and saves it to db

PUT: /cars/{id} - updates all car fields by its id

POST: /workers - creates a new master entity and saves it to db

PUT: /workers/{id} - updates all master fields by id

GET: /workers/{id}/orderings - returns all master orders by id

GET: /workers/{id}/salary - calculate and get worker's salary by id

POST: /orderings - creates a new ordering entity and saves it to db

PUT: /orderings/{id} - updates all order fields by its id

PUT: /orderings/{id}/product - adds product id to product ids list of order by its id

PUT: /orderings/status - updates order status by its id

GET: /orderings/{id}/price - calculates price of order by its id

POST: /owners - creates a new car entity and saves it to db

PUT: /owners/{id} - updates all owner fields by its id

GET: /owners/{id}/orderings - returns all owner orders by its id

POST: /products - creates a new product entity and saves it to db

PUT: /products/{id} - updates all product fields by its id

POST: /favors - creates a new service entity and saves it to db

PUT: /favors/{id} - updates all service fields by its id

PUT: /favors/{id}/status - updates service status by its id
