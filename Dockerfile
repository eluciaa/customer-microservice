FROM openjdk:17
EXPOSE 8082
ADD target/ms-customer-0.0.1-SNAPSHOT.jar ms-customer.jar
ENTRYPOINT ["java","-jar","/ms-customer.jar"]