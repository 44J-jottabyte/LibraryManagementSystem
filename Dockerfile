FROM openjdk:17-jdk-slim
WORKDIR /workspace
COPY build/libs/LibraryManagementSystem-0.0.1-SNAPSHOT.jar /workspace/LibraryManagementSystem-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/workspace/LibraryManagementSystem-0.0.1-SNAPSHOT.jar"]