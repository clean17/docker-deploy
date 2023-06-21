#!/bin/sh

java -Dspring.profiles.active=prod \
     -Dfile.encoding=UTF-8 \
     -Djava.security.egd=file:/dev/./urandom \
     -Dsun.net.inetaddr.ttl=0 \
     -DMYSQL_USER=${MYSQL_USER} \
     -DMYSQL_PASSWORD=${MYSQL_PASSWORD} \
     -DMYSQL_DATABASE=${MYSQL_DATABASE} \
     -DMYSQL_PORT=${MYSQL_PORT} \
     -DMYSQL_HOST=${MYSQL_HOST} \
     -jar \
     /myApp.jar