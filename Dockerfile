FROM adoptopenjdk:12-jre-hotspot

RUN mkdir /app

WORKDIR /app

ADD ./api/target/api-1.0.0-SNAPSHOT.jar /app

EXPOSE 3333

CMD ["java", "-jar", "api-1.0.0-SNAPSHOT.jar"]
#ENTRYPOINT ["java", "-jar", "api-1.0.0-SNAPSHOT.jar"]
#CMD java -jar api-1.0.0-SNAPSHOT.jar
