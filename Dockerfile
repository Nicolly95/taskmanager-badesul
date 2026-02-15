# Estágio de Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
COPY . .
RUN ./mvnw clean package -DskipTests

# Estágio de Execução
FROM eclipse-temurin:17-jdk-alpine
# Este comando procura qualquer arquivo .jar na pasta target e copia
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
# Adicionamos parâmetros para ajudar o Spring a subir em ambientes pequenos
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "--spring.datasource.driverClassName=org.h2.Driver", "--spring.datasource.username=sa", "--spring.datasource.password=", "--spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"]