FROM amazoncorretto:21

# Install Maven
RUN yum install -y maven

# Build JAR
RUN mvn clean verify

# Copy your application JAR file
COPY target/endurance-0.0.1-SNAPSHOT.jar app.jar

# Set the entry point
ENTRYPOINT ["java", "-jar", "/app.jar"]
