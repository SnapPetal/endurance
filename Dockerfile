FROM amazoncorretto:21

# Define environment variables
ENV MAVEN_VERSION 3.8.4
ENV MAVEN_HOME /usr/share/maven
ENV PATH ${MAVEN_HOME}/bin:${PATH}

# Install curl and Maven
RUN apk add --no-cache curl \
  && curl -fsSL https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz | tar -xzC /usr/share \
  && mv /usr/share/apache-maven-${MAVEN_VERSION} /usr/share/maven \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

# Verify installation
RUN mvn --version

# Set the working directory
WORKDIR /app

# Copy your project files
COPY . .

# Build JAR
RUN mvn clean verify

# Set the entry point
ENTRYPOINT ["java", "-jar", "target/endurance-0.0.1-SNAPSHOT.jar"]
