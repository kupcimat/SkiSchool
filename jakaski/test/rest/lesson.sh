#!/bin/bash

# Check parameters
if [ $# -ne 3 ]
then
  echo "Usage: $0 <GET|POST|PUT|DELETE> <object-uri> <json-file>"
  exit 1
fi

# Curl wrapper functions
function getObject {
  URL=$1
  curl -v --header "Accept: application/json" $URL
}

function postObject {
  URL=$1
  JSON=$2
  curl -v --header "Accept: application/json" \
          --header "Content-Type: application/json" \
          --request POST \
          --data @$JSON \
          $URL
}

function putObject {
  URL=$1
  JSON=$2
  curl -v --header "Accept: application/json" \
          --header "Content-Type: application/json" \
          --request PUT \
          --data @$JSON \
          $URL
}

function deleteObject {
  URL=$1
  curl -v --header "Accept: application/json" \
          --request DELETE \
          $URL
}

# Configure parameters
METHOD=$1
URL=http://127.0.0.1:9000$2
JSON=$3

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
  postObject $URL $JSON
fi

# Update object
if [ $METHOD == "PUT" ]
then
  echo "===== PUT: $URL ====="
  putObject $URL $JSON
fi

# Delete object
if [ $METHOD == "DELETE" ]
then
  echo "===== DELTE: $URL ====="
  deleteObject $URL
fi

