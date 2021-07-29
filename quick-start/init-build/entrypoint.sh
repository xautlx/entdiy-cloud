#!/bin/bash

echo Start mvn package for java project...
cd /entdiy-cloud
java -version
mvn package

echo Start yarn build for web project...
cd /entdiy-cloud/entdiy-ui
yarn
yarn build-info
yarn build

echo "**********************************************"
echo "***   Build success, going to next step    ***"
echo "**********************************************"

#tail -f /dev/null
