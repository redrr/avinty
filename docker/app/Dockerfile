FROM maven:3.6.3-jdk-11-openj9 as mvn
COPY ../../src /opt/app/src
COPY ../../pom.xml /opt/app
RUN mvn -f /opt/app/pom.xml clean install -DskipTests

FROM adoptopenjdk/openjdk11:alpine-jre
RUN mkdir /opt/app
COPY --from=mvn /opt/app/target/hr-DEMO.jar /opt/app/hr.jar
ENTRYPOINT ["java","-jar","/opt/app/hr.jar"]
EXPOSE "8085"
