FROM openjdk:8

MAINTAINER Li Xia <xautlx@hotmail.com>

ENV TZ "Asia/Shanghai"

RUN sed -i "s@http://deb.debian.org@http://mirrors.aliyun.com@g" /etc/apt/sources.list
RUN cat /etc/apt/sources.list
RUN rm -Rf /var/lib/apt/lists/*
#
#
RUN apt-get update
#
RUN apt-get install -y net-tools telnet


