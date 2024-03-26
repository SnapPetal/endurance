# Use the official Maven image
FROM maven:3.8.4-openjdk-21

# Set the working directory
WORKDIR /app

# Copy your project files
COPY . .

# Build JAR
RUN mvn clean verify

# Set the entry point
ENTRYPOINT ["java", "-jar", "target/endurance-0.0.1-SNAPSHOT.jar"]
