FROM openjdk:17
COPY target/demo*.jar /usr/src/TriviaGame.jar
COPY src/main/resources/application.properties /opt/conf/application.properties
CMD ["java", "-jar", "/usr/src/TriviaGame.jar", "--spring.config.location=file:/opt/conf/application.properties"]

