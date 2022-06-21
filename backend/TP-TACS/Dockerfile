FROM gradle:7.4-jdk18-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build --no-daemon -x test


FROM openjdk:18-jdk-alpine
EXPOSE 4000
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/TP-TACS-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
