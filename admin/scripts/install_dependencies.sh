#!/bin/bash

echo install dependencies
if [ -d /opt/bootstrap_vue ];then
    echo /opt/bootstrap_vue exists
else
    mkdir -p /opt/bootstrap_vue
fi
echo install dependencies done!