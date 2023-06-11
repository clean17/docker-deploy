#!/bin/bash
mysql $MYSQL_DATABASE < /docker-entrypoint-initdb.d/2_create_table.sql
