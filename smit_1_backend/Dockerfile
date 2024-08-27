# Base image with JDK 22
FROM eclipse-temurin:22-jdk

# Install Maven manually, since there is no official image with Java 22
RUN apt-get update && \
    apt-get install -y maven

# Set the working directory
WORKDIR /app

# Copy pom.xml file and set dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build application
RUN mvn clean install -DskipTests

# Run application
CMD ["mvn", "spring-boot:run"]

