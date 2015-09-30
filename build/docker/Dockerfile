FROM java:8
MAINTAINER Kaj Van der Hallen <kaj.vanderhallen@foresee.be>
EXPOSE 8080
ENV JAVA_OPTS -Dspring.profiles.active=docker
ADD customerside-composite-service.tar /
ENTRYPOINT ["/customerside-composite-service/bin/customerside-composite-service"]
