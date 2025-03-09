#!/bin/bash

# Function to print usage instructions
usage() {
  echo "Usage: $0 -t <TPS> -i <Interval> -d <Duration> -u <URL> [-h]"
  echo "  -t <TPS>         Transactions Per Second"
  echo "  -i <Interval>    Interval between requests (in seconds)"
  echo "  -d <Duration>    Duration for which the requests will run (in seconds)"
  echo "  -u <URL>         URL endpoint to call"
  echo "  -h               Show this help message"
  exit 1
}

# Parse command-line arguments
while getopts "t:i:d:u:h" opt; do
  case $opt in
    t)
      TPS=$OPTARG
      ;;
    i)
      INTERVAL=$OPTARG
      ;;
    d)
      DURATION=$OPTARG
      ;;
    u)
      ENDPOINT_URL=$OPTARG
      ;;
    h)
      usage
      ;;
    *)
      usage
      ;;
  esac
done

# Validate parameters
if [[ -z "$TPS" ]] || [[ -z "$INTERVAL" ]] || [[ -z "$DURATION" ]] || [[ -z "$ENDPOINT_URL" ]]; then
  usage
fi

# Calculate the interval for sleep based on TPS
#EXPECTED_INTERVAL=$(echo "scale=4; 1 / $TPS" | bc)
EXPECTED_INTERVAL=$(echo "$INTERVAL")

# Start time
START_TIME=$(date +%s)

echo "Starting to call $ENDPOINT_URL with $TPS TPS, Interval of $EXPECTED_INTERVAL seconds, for $DURATION seconds."

# Main loop
while :; do
  # Current time
  CURRENT_TIME=$(date +%s)
  
  # Check if duration has elapsed
  ELAPSED=$((CURRENT_TIME - START_TIME))
  if (( ELAPSED >= DURATION )); then
    break
  fi

	xargs -I% -P0 curl -X GET "$ENDPOINT_URL"< <(printf '%s\r\n' {1..100})


  # Make the request in the background
  #(
    # Record the start time for the request
    #REQUEST_START_TIME=$(date +%s.%N)
    
    # Make the request and capture the response time using curl
    #RESPONSE=$(curl -o /dev/null -s -w "%{time_total}\n" -X GET "$ENDPOINT_URL")
    
    # Record the end time for the request
    #REQUEST_END_TIME=$(date +%s.%N)
    
    # Calculate the response time
    #RESPONSE_TIME=$(echo "$RESPONSE" | awk '{print $1}')
    
    # Print the response time for the request
    #echo "Response Time: ${RESPONSE_TIME}s"
  #) &
  
  # Sleep for the calculated interval based on TPS
  #sleep "$EXPECTED_INTERVAL"
  sleep 1
done

# Wait for all background jobs to finish
wait

echo "Finished."
