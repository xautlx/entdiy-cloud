#!/bin/sh

export CUR_DIR="$(  pwd  )"
#echo "Using CUR_DIR: ${CUR_DIR} "
export SHELL_DIR="$( cd "$( dirname "$0"  )" && pwd  )"
#echo "Using SHELL_DIR: ${SHELL_DIR} "
export BASE_DIR="$( cd "${SHELL_DIR}/." && pwd  )"
#echo "Using BASE_DIR: ${BASE_DIR}"

echo Startup MySQL/Redis service...
cd ${BASE_DIR}/basis-service
docker-compose down
docker-compose up -d --build
echo Sleep 15s for MySQL startup...
sleep 15s

echo Startup Nacos/Loki/Grafana service...
cd ${BASE_DIR}/support-service
docker-compose down
docker-compose up -d --build

echo Startup Maven/NodeJS build project...
cd ${BASE_DIR}/init-build
docker-compose up --build
docker-compose down
docker rm -f entdiy-build

echo Startup APP Docker service...
cd ${BASE_DIR}/apps-service
docker-compose down
docker-compose up -d --build

cd ${CUR_DIR}
echo "**********************************************"
echo "*** All service build and startup success.  **"
echo "*** Please visit http://127.0.0.1/admin/    **"
echo "**********************************************"
