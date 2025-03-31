FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file (replace with your actual filename)
COPY target/challenge-0.0.1-SNAPSHOT.jar app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
