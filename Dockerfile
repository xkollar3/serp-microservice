FROM maven:3.6.3-openjdk-17
WORKDIR /serp-microservice

COPY ./pom.xml ./pom.xml
COPY ./src ./src

# Build the application
RUN mvn clean package

# Run the application
CMD ["java", "-jar", "target/serp-microservice-0.0.1-SNAPSHOT.jar"]