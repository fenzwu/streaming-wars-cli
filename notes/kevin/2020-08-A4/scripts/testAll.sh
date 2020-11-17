DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
regex="commands_([0-9]+).txt"

for test in $DIR/../test_scenarios/*;
do
  if [[ $test =~ $regex ]]
    then
        test_num="${BASH_REMATCH[1]}"
        $DIR/test.sh $test_num
    fi
done