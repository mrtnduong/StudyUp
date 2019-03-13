#!/usr/bin/env bash
set -e

if [ -z $5 ]; then
    export REDIS_HOST=redis
fi

sed -i'' "s/%{REDIS_HOST}/${REDIS_HOST}/" /etc/nginx/nginx.conf

exec "$@"