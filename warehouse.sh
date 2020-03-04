#!/usr/bin/env sh

cd warehouse
maven clean install
cd target
java -jar warehouse-0.0.1-SNAPSHOT.jar