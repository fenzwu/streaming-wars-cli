A4 Repo for CS6310 Fall 2020
# To Install Docker go to:
```
https://docs.docker.com/get-docker/
```

# Note please run all scripts from the project root directory

### To build:

```
docker build -t gatech/streamingwars -f Dockerfile ./
```

### To copy your jar to host:
#### Mac / Linux
```
./scripts/copy.sh
```
#### Windows
```
.\scripts\copy.sh
```

### To test a specific scenario against the initial jar
#### Mac / Linux
```
./scripts/test.sh <scenario>
```
#### Windows
```
.\scripts\test.sh <scenario>
```

### To run in interactive mode
#### Step 1 from the host 
```
docker run -ti gatech/streamingwars sh
```
#### Step 2 from the container
```
java -jar streaming_wars.jar
```
#### Step 3 from the jar
* From there you can run any of the commands listed from the assignment:
```
create_demo,<short name>,<long name>,<number of accounts>
create_studio,<short name>,<long name>
create_event,<type>,<name>,<year produced>,<duration>,<studio>,<license fee>
create_stream,<short name>,<long name>,<subscription price>
offer_movie,<streaming service>,<movie name>
offer_ppv,<streaming service>,<pay-per-view name>,<price>
watch_event,<demographic group>,<percentage>,<streaming service>,<event name>
next_month
display_demo,<short name>
display_stream,<short name>
display_studio,<short name>
display_events
display_offers
display_time
stop
```

### To run & test in interactive mode

```
java -jar streaming_wars.jar < commands_0.txt > stream_test_0_results.txt
java -jar streaming_wars_initial.jar < commands_0.txt > stream_test_initial_0_results.txt
diff -s stream_test_0_results.txt stream_test_initial_0_results.txt > results_0.txt
```

### To run a specific scenario with your jar and output to localhost

```
docker run gatech/streamingwars java -jar streaming_wars.jar < commands_0.txt > stream_test_0_results.txt
```

### If you get stuck in an infinite loop
Simply stop and remove the running container
```
docker ps
docker rm -f <container_id>
```
