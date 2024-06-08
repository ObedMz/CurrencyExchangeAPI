# Etapa 1: Compilar la aplicación
FROM gradle:jdk17-alpine as build

WORKDIR /app

# Copia el código fuente al contenedor
COPY . /app

# Compila la aplicación
RUN ./gradlew build --no-daemon

# Etapa 2: Ejecutar la aplicación
FROM openjdk:17-slim

WORKDIR /app

# Copia el JAR compilado de la etapa de compilación anterior
COPY --from=build /app/build/libs/projects-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080
# Comando para ejecutar la aplicación cuando se inicie el contenedor
CMD ["java", "-jar", "app.jar"]
