#!/bin/sh

# DB 초기화가 끝난뒤에 스프링 부트 실행

# 에러 반환시 즉시 중단
set -e

# 스크립트에 전달된 첫번째 인수를 저장
host="$1"

# 왼쪽으로 이동 $2 -> $1, $1은 삭제
shift

# SELECT 1 -> 반복 확인 루프, 1초 마다
until mysql -h"$host" -P3306 -umetacoding -pmetacoding -e 'SELECT 1'; do
  >&2 echo "MySQL is unavailable - sleeping"
  sleep 1
done

# 준비됨
>&2 echo "MySQL is up - executing command"


java -Dspring.profiles.active=dev -Dfile.encoding=UTF-8 \
     -Djava.security.egd=file:/dev/./urandom -Dsun.net.inetaddr.ttl=0 \
     -DHS512_SECRET=${HS512_SECRET} \
     -DMYSQL_USER=${MYSQL_USER} \
     -DMYSQL_PASSWORD=${MYSQL_PASSWORD} \
     -DMYSQL_DATABASE=${MYSQL_DATABASE} \
     -DMYSQL_PORT=${MYSQL_PORT} \
     -jar /app.jar