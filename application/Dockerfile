FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
COPY target/stc-assessment-0.0.1-SNAPSHOT.jar stc.jar
ENTRYPOINT ["java","-jar","/stc.jar"]
