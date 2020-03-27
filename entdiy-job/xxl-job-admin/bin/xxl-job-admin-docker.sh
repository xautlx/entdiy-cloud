#!/bin/bash

docker run -p 8080:8080 restart=always \
       --name xxl-job-admin \
       -v /tmp:/data/applogs \
       -d xuxueli/xxl-job-admin