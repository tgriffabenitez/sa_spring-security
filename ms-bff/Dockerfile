# Variables globales para todos los builders
ARG MSCV_NAME=ms-bff

FROM openjdk:17-jdk-alpine as builder

# Llamo a la variable global para usarla en este builder
ARG MSCV_NAME

WORKDIR /app/$MSCV_NAME

COPY ./pom.xml /app
COPY  ./.mvn ./.mvn
COPY mvnw .
COPY pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target

COPY /src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app
RUN mkdir ./logs

# Llamo a la variable global para usarla en este builder
ARG MSCV_NAME

ARG TARGET_DIR=/app/$MSCV_NAME/target

COPY --from=builder $TARGET_DIR/ms-bff-0.0.1-SNAPSHOT.jar .

EXPOSE 443

CMD ["java", "-jar", "ms-bff-0.0.1-SNAPSHOT.jar"]