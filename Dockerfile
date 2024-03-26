FROM amazoncorretto:21

RUN yum install -y tar which gzip # TODO remove

# common for all images
ENV MAVEN_HOME /usr/share/maven

COPY --from=maven:3.9.6-eclipse-temurin-11 ${MAVEN_HOME} ${MAVEN_HOME}
COPY --from=maven:3.9.6-eclipse-temurin-11 /usr/local/bin/mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
COPY --from=maven:3.9.6-eclipse-temurin-11 /usr/share/maven/ref/settings-docker.xml /usr/share/maven/ref/settings-docker.xml

RUN ln -s ${MAVEN_HOME}/bin/mvn /usr/bin/mvn

ARG MAVEN_VERSION=3.9.6
ARG USER_HOME_DIR="/root"
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

# Set the working directory
WORKDIR /app

# Copy your project files
COPY . .

# Build JAR
RUN mvn clean verify

# Set the entry point
ENTRYPOINT ["java", "-jar", "target/endurance-0.0.1-SNAPSHOT.jar"]
