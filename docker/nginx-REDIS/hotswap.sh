#!/usr/bin/env bash

if [ -n $1 ]; then
   export REDIS_HOST=$1
fi

sed -i "s/server.*:/server ${REDIS_HOST}:/" /etc/nginx/nginx.conf

eval "$(/usr/sbin/nginx -s reload)"
