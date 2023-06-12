#!/bin/bash
mysql -u $MYSQL_USER -p$MYSQL_PASSWORD $MYSQL_DATABASE < /docker-entrypoint-initdb.d/2_create_table.sql
