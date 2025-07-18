# Use Maven to build the app
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app
COPY . .

# Go into the Spring Boot folder and build
WORKDIR /app/Invoisecure
RUN mvn clean package -DskipTests

# Use a lighter JRE image to run the app
FROM eclipse-temurin:17-jdk-alpine

# Copy the built JAR from the builder stage
COPY --from=build /app/Invoisecure/target/*.jar app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
