# Base image com JDK 17 e Maven
FROM maven:3.8-openjdk-17-slim as build
WORKDIR /app
COPY pom.xml .
# Baixa as dependências do Maven
RUN mvn dependency:go-offline
COPY src src
# Compila o aplicativo
RUN mvn package -DskipTests

# Imagem de produção que contém apenas o JAR compilado e o Java Runtime Environment
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/*.jar cityconnect.jar

CMD echo "America/Fortaleza" > /etc/timezone && dpkg-reconfigure -f noninteractive tzdata

ENV TZ America/Fortaleza

EXPOSE 80
CMD ["java", "-jar", "cityconnect.jar"]