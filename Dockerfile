FROM maven:3.3.9-jdk-8-alpine
COPY . /app
WORKDIR /app
RUN mvn install
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","target/spotippos.jar"]
EXPOSE 8080