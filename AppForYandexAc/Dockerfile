FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/AppForYandexAc-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app-for-ya.jar
ENTRYPOINT ["java","-jar","/app-for-ya.jar"]
EXPOSE 80
