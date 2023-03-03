#Application Description
This Spring Boot application allows users to place bets on participants in a sporting event. The race involves cars of the Ferrari, BMW, Audi, and Mercedes brands.

The application has two endpoints:

1. `POST /bet` - accepts the car brand (Ferrari, BMW, Audi, or Mercedes) and bet amount in JSON format. Upon successful bet placement, it returns an HTTP status code of 200.
2. `GET /bet` - accepts an optional car parameter with the car brand and returns the total bet amount on all cars or only on the car with the specified brand.

### Running the Application

The application can be run in the following ways:

1. Using Maven:

    mvn spring-boot:run
2. Using Java:

    java -jar target/myapp.jar

###Running Tests
To run tests, execute the following command in the project root directory:

mvn test