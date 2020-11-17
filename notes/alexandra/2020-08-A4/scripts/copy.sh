mkdir -p ./submission

docker run -d --name sample gatech/streamingwars tail -f /dev/null
docker cp sample:/usr/src/a4/streaming_wars.jar ./submission/streaming_wars.jar
docker rm -f sample > /dev/null
