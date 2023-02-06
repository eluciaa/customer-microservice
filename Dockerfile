FROM openjdk:17
EXPOSE 8082
ADD target/ms-customer-1.0.0.jar ms-customer.jar
ENTRYPOINT ["java","-jar","/ms-customer.jar"]