#!/bin/bash

# Configuration
ENDPOINT_URL="https://localhost:8080/java-benchmark/platform-thread"  # Replace with your endpoint
REQUEST_METHOD="GET"  # Change to POST, PUT, etc., as needed
REQUEST_PAYLOAD='{}'  # JSON payload for POST/PUT requests (if needed)
HEADERS="-H 'Authorization: Bearer your_token'"  # Add headers if necessary ex -H 'Authorization: Bearer your_token'
TPS=10  # Transactions Per Second
DURATION=60  # Duration in seconds

# Calculate the interval between requests (in seconds)
INTERVAL=$(echo "scale=4; 1 / $TPS" | bc)

# Start time
START_TIME=$(date +%s)

echo "Starting to call $ENDPOINT_URL with $TPS TPS for $DURATION seconds."

# Main loop
while :; do
  # Current time
  CURRENT_TIME=$(date +%s)
  
  # Check if duration has elapsed
  ELAPSED=$((CURRENT_TIME - START_TIME))
  if (( ELAPSED >= DURATION )); then
    break
  fi

  # Make the request
  if [[ "$REQUEST_METHOD" == "GET" ]]; then
    curl -X GET $HEADERS "$ENDPOINT_URL" &
  elif [[ "$REQUEST_METHOD" == "POST" ]]; then
    curl -X POST $HEADERS -d "$REQUEST_PAYLOAD" "$ENDPOINT_URL" &
  else
    echo "Unsupported method: $REQUEST_METHOD"
    exit 1
  fi

  # Sleep for the calculated interval
  sleep "$INTERVAL"
done

echo "Finished."
