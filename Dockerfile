FROM java:8-jdk-alpine

COPY ./target/et-ingress-1.0.0.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch et-ingress-1.0.0.jar'

ENTRYPOINT ["java","-jar","et-ingress-1.0.0.jar"]