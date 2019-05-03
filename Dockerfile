FROM openjdk:8-jdk-alpine

ADD target/zk-redis.jar /opt/app.jar

# install dockerize
# from https://github.com/powerman/dockerize
RUN wget -O - https://github.com/powerman/dockerize/releases/download/v0.10.0/dockerize-`uname -s`-`uname -m` | install /dev/stdin /bin/dockerize

# wait for Redis before running Spring Boot application
CMD [ "/bin/dockerize", "-wait", "tcp://redis:6379", "java", "-Djava.security.egd=file:/dev/./urandom","-jar", "/opt/app.jar"]
