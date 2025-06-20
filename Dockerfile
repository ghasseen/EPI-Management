FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# -----------------------------
# Étape finale
# -----------------------------
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copier l’application jar
COPY --from=build /app/target/*.jar app.jar

# Copier le script wait-for-it.sh
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

EXPOSE 8080

# Attendre que MySQL soit disponible avant de lancer Spring Boot
ENTRYPOINT ["/wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "app.jar"]


