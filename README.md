# autoservice

**This is a RESTful app built using Spring Boot. It can process orders and favours of a car owner**

#### Functionality:
- creating and storing new entities - cars, owners, workers, favors, etc...
- updating any data any changing statuses
- calculating cost of ordering
- calculating salary of workers

#### Structure of the project:
- Repository layer - persistence layer that communicates with DB
- Service layer - business logic
- Controllers - accept requests and send responses

#### Technologies used in the project:
- Spring Boot 3
- Java 17
- Postgres
- Hibernate
- Lombok
- Maven
- JUnit
- Mockito

#### Instructions:
- download the project
- install postgreSQL or use Docker image to run it
- configure [application.properties](src/main/resources/application.properties) to match your db username and password
- run [main method](src/main/java/ua/masaltsev/autoservice2/Autoservice2Application.java)
- open [swagger UI](http://localhost:8080/swagger-ui/index.html#/) to communicate with an API

#### List of available end-points:

POST: /cars - creates a new car entity and saves it to db

PUT: /cars/{id} - updates all car fields by its id

POST: /masters - creates a new master entity and saves it to db

PUT: /masters/{id} - updates all master fields by its id

GET: /masters/{id}/orders - returns all master orders by its id

POST: /orders - creates a new order entity and saves it to db

PUT: /orders/{id} - updates all order fields by its id

PUT: /orders/{id}/add-product - adds product id to product ids list of order by its id

PUT: /orders/{id}/status - updates order status by its id

GET: /orders/{id}/cost - calculates final price of order by its id

POST: /owners - creates a new car entity and saves it to db

PUT: /owners/{id} - updates all owner fields by its id

GET: /owners/{id}/orders - returns all owner orders by its id

POST: /products - creates a new product entity and saves it to db

PUT: /products/{id} - updates all product fields by its id

POST: /services - creates a new service entity and saves it to db

PUT: /services/{id} - updates all service fields by its id

PUT: /services/{id}/status - updates service status by its id
