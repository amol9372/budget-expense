FROM adoptopenjdk/openjdk11:latest
WORKDIR /app
ARG JAR_FILE=target/expenseapp-1.0.jar
ADD ${JAR_FILE} .
ENTRYPOINT ["java","-jar","expenseapp-1.0.jar"]