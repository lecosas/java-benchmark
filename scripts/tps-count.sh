previous_count=0
previous_time=$(date +%s%N)  # Get the time in nanoseconds

while true; do
  current_count=$(curl -X POST -d '{"url": "/todos/1"}' http://localhost:1080/__admin/requests/count | jq '.count')
  current_time=$(date +%s%N)  # Get the new time in nanoseconds
  
  #echo "Count: $current_count"
  
  # Ensure curl returned a valid number
  if [[ ! "$current_count" =~ ^[0-9]+$ ]]; then
    echo "Error: Invalid response from WireMock"
    sleep 1
    continue
  fi

  # Calculate elapsed time in seconds (ensure at least 1 millisecond to avoid division by zero)
  elapsed_time=$(( (current_time - previous_time) / 1000000 ))  # Convert ns to ms
  if [[ "$elapsed_time" -lt 1 ]]; then
    elapsed_time=1  # Ensure at least 1ms
  fi

  # Compute RPS (convert ms to seconds)
  rps=$(( (current_count - previous_count) * 1000 / elapsed_time ))

  echo "Requests per second: $rps"

  # Update for next iteration
  previous_count=$current_count
  previous_time=$current_time

  sleep 1
done