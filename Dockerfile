FROM gradle:jdk17-alpine as build

WORKDIR /app

COPY . /app

RUN chmod +x gradlew

RUN ./gradlew build --no-daemon


FROM openjdk:17-slim

WORKDIR /app

COPY --from=build /app/build/libs/projects-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
