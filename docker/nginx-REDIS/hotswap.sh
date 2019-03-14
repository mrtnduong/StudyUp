#!/usr/bin/env bash

if [ -n $1 ]; then
   export REDIS_HOST=$1
fi

if grep -q "server $1" /etc/nginx/nginx.conf; then
   echo "Passed parameter currently in use."
else
  sed -i "s/server.*:/server ${REDIS_HOST}:/" /etc/nginx/nginx.conf
  eval "$(/usr/sbin/nginx -s reload)"
fi
