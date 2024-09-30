FROM openjdk:21

WORKDIR /app

COPY target/al-*.jar /app/
COPY src/main/resources/prod/* /app/

ENTRYPOINT ["java", "-jar", "al-start-boot.jar"]
