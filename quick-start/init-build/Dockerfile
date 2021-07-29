FROM openjdk:8

MAINTAINER Li Xia <xautlx@hotmail.com>

ENV TZ "Asia/Shanghai"

WORKDIR /entdiy-cloud

COPY sources.list /etc/apt/sources.list
#
#
RUN apt-get update
#
#RUN apt-get install net-tools
#RUN apt-get install telnet

RUN apt-get install -y maven nodejs npm --fix-missing
RUN echo "Maven: " && mvn -v
RUN echo "Node: " && node -v
RUN echo "NPM: " && npm -v

RUN npm config set registry http://registry.npm.taobao.org
RUN npm install -g yarn
RUN echo "YARN: " && yarn -v
RUN yarn config set registry https://registry.npm.taobao.org -g

COPY entrypoint.sh /
RUN chmod +x /entrypoint.sh


