# Use the latest Alpine image as the base
FROM alpine:latest

# Install the OpenJDK 21 package
RUN apk update && apk add openjdk21-jdk

# Set JAVA_HOME environment variable
ENV JAVA_HOME /usr/lib/jvm/java-21-openjdk

# Verify the installation
RUN java --version

RUN mkdir /apps

# copy jar file into app directory
ADD build/libs/atm-monitor-client.jar /apps/atm-monitor-client.jar

# dir change into apps
WORKDIR /apps

#expose port
EXPOSE 8080

# execute jar
CMD ["java" ,"-jar" , "atm-monitor-client.jar"]