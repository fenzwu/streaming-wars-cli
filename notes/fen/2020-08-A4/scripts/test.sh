#!/usr/bin/env bash

SCENARIO=$1

mkdir -p ./test_results
docker run --name streamingwars gatech/streamingwars sh -c "\
    java -jar streaming_wars.jar < commands_${SCENARIO}.txt > stream_test_${SCENARIO}_results.txt && \
    java -jar streaming_wars_initial.jar < commands_${SCENARIO}.txt > stream_test_initial_${SCENARIO}_results.txt && \
    diff -s stream_test_${SCENARIO}_results.txt stream_test_initial_${SCENARIO}_results.txt > diff_results_${SCENARIO}.txt && \
    cat diff_results_${SCENARIO}.txt"
docker cp streamingwars:/usr/src/a4/stream_test_${SCENARIO}_results.txt ./test_results/
docker cp streamingwars:/usr/src/a4/stream_test_initial_${SCENARIO}_results.txt ./test_results/
docker cp streamingwars:/usr/src/a4/diff_results_${SCENARIO}.txt ./test_results
docker rm streamingwars > /dev/null
