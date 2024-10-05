FROM openjdk:21

WORKDIR /app

COPY target/al-*.jar /app/

ENTRYPOINT ["java", "-jar", "al-lowcode.jar"]