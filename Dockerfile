FROM openjdk:14
COPY . /app
WORKDIR /app
RUN ./gradlew build
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/Alfa_Test_Currencies-0.0.1-SNAPSHOT.jar"]