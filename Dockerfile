#FROM nexus.arce.ce.gov.br:8082/glowroot-arce:1 as glowroot-stage

FROM openjdk:8-jdk-alpine as up-stage

# The application's jar file
ARG JAR_FILE=target/*.jar

# Copy glowroot files from glowroot-stage
#COPY --from=glowroot-stage /opt/glowroot /opt/glowroot

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# "-javaagent:/opt/glowroot/glowroot.jar",
# Run the jar file
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
