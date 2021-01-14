## DOCKER hadoop BASE
FROM ubuntu:20.04

MAINTAINER charles <lcs.005@163.com>

ENV SSH_PORT=9022
ENV HADOOP_HOME="/opt/hadoop-3.3.0"
ENV JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64"

RUN apt update && apt-get install -y --no-install-recommends \
           openssh-server \
           openjdk-8-jdk \
           curl \
           net-tools

RUN curl -o /tmp/hadoop.tar.gz https://mirrors.bfsu.edu.cn/apache/hadoop/core/hadoop-3.3.0/hadoop-3.3.0.tar.gz
RUN tar -xzvf  /tmp/hadoop.tar.gz -C /opt/ --exclude=hadoop-3.3.0/share/doc

RUN mkdir /run/sshd \
    && echo "Port=$SSH_PORT" >> /etc/ssh/sshd_config

RUN  apt-get clean \
    && rm -rf /tmp/* \
    && rm -rf /var/cache
    && rm -rf /var/lib/apt
    && rm -rf /var/lib/dpkg

EXPOSE $SSH_PORT 9000 9864 9866 9867 9868 9870