# Copy the jar and test scenarios into our final image
FROM openjdk:15-alpine
WORKDIR /usr/src/a6
COPY cs6310_a6_15.jar ./
