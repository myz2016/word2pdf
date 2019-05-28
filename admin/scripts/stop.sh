#!/bin/bash

APPNAME=word2pdf-admin
VERSION=0.1-SNAPSHOT
echo going to stop $APPNAME
ps aux | grep $APPNAME | awk '{print "kill -9 " $2}' | bash
processesNum=`ps aux | grep $APPNAME | grep -v grep | wc -l | sed 's/ //g'`
if [ "$processesNum" == "0" ];then
    echo stopped app successfully!
    exit 0
else
    echo stopped app failed!
    exit 1
fi 