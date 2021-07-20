FROM openjdk:11.0.11-jre-slim

ENV spring.profiles.active=default
ENV JAVA_APP_DIR=/deployments/camel-starter
ENV TZ=Europe/Moscow

VOLUME /tmp $JAVA_APP_DIR/logs

COPY image $JAVA_APP_DIR

WORKDIR $JAVA_APP_DIR

ENTRYPOINT [ "java", "-cp", "*:lib/*", "arina.camel.Starter" ]
