FROM gradle:jdk21-alpine

ARG PRODUCTION
ARG JDBC_DATABASE_PASSWORD
ARG JDBC_DATABASE_URL
ARG JDBC_DATABASE_USERNAME

ENV PRODUCTION ${PRODUCTION}
ENV JDBC_DATABASE_PASSWORD ${JDBC_DATABASE_PASSWORD}
ENV JDBC_DATABASE_URL ${JDBC_DATABASE_URL}
ENV JDBC_DATABASE_USERNAME ${JDBC_DATABASE_USERNAME}

RUN apk add docker docker-cli docker-cli-compose
RUN apk add openrc
RUN rc-update add docker

WORKDIR /app
COPY ./build/libs/Fashionpedia-Payment-0.0.1-dev.jar /app
COPY ./docker-compose.yml /app
COPY ./monitoring /app

EXPOSE 8080
EXPOSE 3000
CMD ["java","-jar","Fashionpedia-Payment-0.0.1-dev.jar"]