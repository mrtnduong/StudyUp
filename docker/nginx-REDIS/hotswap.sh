#!/usr/bin/env bash

if [ -n $5 ]; then
    export REDIS_HOST=$5
fi

sed -i'' "s/%{REDIS_HOST}/${REDIS_HOST}/" /etc/nginx/nginx.conf

eval $(/usr/sbin/nginx -s reload)
