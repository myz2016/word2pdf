#!/bin/bash

APPNAME=word2pdf-admin
VERSION=0.1-SNAPSHOT
basepath=$(cd `dirname $0`; pwd)
cd ${basepath}
cd ..
basepath=$(cd `dirname $0`; pwd)
echo going to start ${APPNAME}
nohup java -jar -Xmx128m -Xbootclasspath/a:$basepath/config ${APPNAME}-$VERSION.jar > ./${APPNAME}_log.txt 2>&1 &
echo app started!
ps aux | grep ${APPNAME} | grep -v grep