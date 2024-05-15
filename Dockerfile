FROM openjdk:23-slim
LABEL authors="J0782-Sean"
ADD target/Spring-Security-0.0.1-SNAPSHOT.jar Spring-Security-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/Spring-Security-0.0.1-SNAPSHOT.jar"]