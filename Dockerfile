FROM amazoncorretto:20-al2023-jdk
# Install Maven
RUN yum install -y maven
COPY target/endurance-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
