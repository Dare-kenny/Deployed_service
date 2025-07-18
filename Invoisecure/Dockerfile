# Use a base image with Java
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Add built jar to the image
COPY target/invoisecure.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
