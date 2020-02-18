FROM openjdk:11-jre
WORKDIR /hw
ADD ./target/hw-0.0.1-SNAPSHOT.jar hw-0.0.1.jar
ENTRYPOINT ["java", "-jar", "hw-0.0.1.jar"]
EXPOSE 8080