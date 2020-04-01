#!/bin/sh

export SHELL_DIR="$( cd "$( dirname "$0"  )" && pwd  )"
echo "Using SHELL_DIR: ${SHELL_DIR}"
export BASE_DIR="$( cd "${SHELL_DIR}/." && pwd  )"
echo "Using BASE_DIR: ${BASE_DIR}"

mkdir -p ${BASE_DIR}/data

docker-compose $*