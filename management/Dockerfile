FROM openjdk:8-jre-alpine
ARG JAR_FILE
RUN mkdir -p "/opt/data/"
COPY "$JAR_FILE" "/opt/data/app.jar"
WORKDIR "/opt/data"
ENTRYPOINT ["java", "-jar", "app.jar"]
