#!/bin/bash

# 데이터베이스 생성 -p패스워드는 붙여야 함
mysql -u root -p$MYSQL_ROOT_PASSWORD -e "CREATE DATABASE ${MYSQL_DATABASE};"

# 사용자에게 데이터베이스에 대한 권한 부여
mysql -u root -p$MYSQL_ROOT_PASSWORD -e "GRANT ALL PRIVILEGES ON ${MYSQL_DATABASE}.* TO $MYSQL_USER@'%';"

# 생성한 데이터베이스 선택
mysql -u $MYSQL_USER -p$MYSQL_PASSWORD -e "USE $MYSQL_DATABASE;"