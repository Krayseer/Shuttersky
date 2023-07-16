FROM openjdk:18
ADD build/libs/shuttersky-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]