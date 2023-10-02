FROM openjdk:17

RUN mkdir /app
COPY simple_service.jar /app
EXPOSE 8080
WORKDIR /app

CMD java -jar simple_service.jar
  
  
  