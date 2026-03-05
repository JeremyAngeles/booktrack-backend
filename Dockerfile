# --- ETAPA 1: CONSTRUCCIÓN (BUILD) ---
# Usamos Maven con Java 21 para compilar
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copiamos el pom.xml y descargamos dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código fuente
COPY src ./src

# Compilamos el .jar saltando los tests para agilizar
RUN mvn clean package -DskipTests

# --- ETAPA 2: EJECUCIÓN (RUNTIME) ---
# Usamos una imagen ligera de Java 21 para correr la app
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copiamos el .jar generado en la etapa anterior
# OJO: Asegúrate que el nombre coincida con tu pom.xml (artifactId + version)
# Si tu pom dice <artifactId>bookscout</artifactId> y <version>0.0.1-SNAPSHOT</version>:
COPY --from=build /app/target/bookscout-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto 8080
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]