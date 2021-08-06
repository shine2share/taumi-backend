FROM openjdk:11
VOLUME /tmp
# Add argument spring profile
ARG ENV_PROFILE
ENV ENV_PROFILE=$ENV_PROFILE
ARG SRC_PATH
ARG DST_PATH
ENV ENV_JAR_PATH=$DST_PATH
ADD $SRC_PATH $DST_PATH
# Run the jar file
ENTRYPOINT exec java $JVM_OPTS -jar $ENV_JAR_PATH --spring.config.location=file:/deployment/config/application.properties --spring.profiles.active=$ENV_PROFILE