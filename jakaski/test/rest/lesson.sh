#!/bin/bash

# Check parameters
if [ $# -ne 2 ]
then
  echo "Usage: $0 <GET | POST> <object-uri>"
  exit 1
fi

# Curl wrapper functions
function getObject {
  URL=$1
  curl -v --header "Accept: application/json" $URL
}

function postObject {
  URL=$1
  curl -v --header "Accept: application/json" \
          --header "Content-Type: application/json" \
          --request POST \
          --data "{ \"lesson\" : { \"start\" : \"2012-12-01 12:41\", \"end\" : \"2012-12-01 13:41\", \"note\" : \"Let's rock with this API!\", \"instructor\" : \"/instructor/23\", \"student\" : \"/student/13\" }}" \
          $URL
}

# Configure parameters
METHOD=$1
URL=http://127.0.0.1:9000$2

# Get object
if [ $METHOD == "GET" ]
then
  echo "===== GET: $URL ====="
  getObject $URL
fi

# Create object
if [ $METHOD == "POST" ]
then
  echo "===== POST: $URL ====="
  postObject $URL
fi

