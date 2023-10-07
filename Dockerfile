FROM openjdk:17

RUN mkdir /app
COPY simple_service.jar /app
EXPOSE 8080
WORKDIR /app

COPY wait-for-db.sh /app/wait-for-db.sh
RUN chmod +x /app/wait-for-db.sh

CMD ["/app/wait-for-db.sh"]
  
  
  