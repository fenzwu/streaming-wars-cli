# Compile our java files in this container
FROM openjdk:15-alpine AS builder
COPY src /usr/src/a4/src
WORKDIR /usr/src/a4
RUN find . -name "*.java" | xargs javac -d ./target
RUN jar cfe streaming_wars.jar Main -C ./target/ .

# Copy the jar and test scenarios into our final image
FROM openjdk:15-alpine
WORKDIR /usr/src/a4
COPY test_scenarios ./
COPY ./bin/* ./
COPY --from=builder /usr/src/a4/streaming_wars.jar ./streaming_wars.jar
CMD ["java", "-jar", "streaming_wars.jar", "commands_0.txt"]
