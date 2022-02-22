FROM adoptopenjdk/openjdk11
ARG JAR_FILE=target/*.jar
ARG FIREBASE_FILE=sideralis-firebase.json

COPY ${JAR_FILE} app.jar
ADD ${FIREBASE_FILE} sideralis-firebase.json
ENTRYPOINT ["java","-jar","/app.jar","--server.servlet.context-path=/executor"]