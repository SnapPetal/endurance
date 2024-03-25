FROM amazoncorretto:21

# Install Maven
RUN yum install -y maven

# Set the working directory
WORKDIR /app

# Copy your project files
COPY . .

# Build JAR
RUN mvn clean verify

# Set the entry point
ENTRYPOINT ["java", "-jar", "target/endurance-0.0.1-SNAPSHOT.jar"]
