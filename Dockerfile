FROM openjdk:11

EXPOSE 8082

ADD ./build/libs/*.jar auth-server.jar

ENTRYPOINT ["java","-jar","/auth-server.jar"]
