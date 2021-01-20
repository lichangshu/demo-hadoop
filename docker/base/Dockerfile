## DOCKER hadoop BASE / microwww/hadoop:v3.3.0-base
FROM ubuntu:20.04

MAINTAINER charles <lcs.005@163.com>

ENV SSH_PORT=9022
ENV HADDOP_VERSION="2.10.1"
ENV HADOOP_HOME="/opt/hadoop-$HADDOP_VERSION"
ENV JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64"

RUN apt update && apt-get install -y --no-install-recommends \
           openssh-server \
           openjdk-8-jdk \
           curl \
           net-tools

RUN curl -o /tmp/hadoop.tar.gz https://mirrors.gigenet.com/apache/hadoop/common/hadoop-2.10.1/hadoop-2.10.1.tar.gz
RUN tar -xvf  /tmp/hadoop.tar.gz -C /opt/ --exclude=hadoop-$HADDOP_VERSION/share/doc

RUN mkdir /run/sshd \
    && echo "Port=$SSH_PORT" >> /etc/ssh/sshd_config

RUN  apt-get clean \
    && rm -rf /tmp/* \
    && rm -rf /var/cache \
    && rm -rf /var/lib/apt \
    && rm -rf /var/lib/dpkg

ENV PATH "$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$PATH"
WORKDIR $HADOOP_HOME

ADD Dockerfile /Dockerfile

EXPOSE $SSH_PORT 9000 9864 9866 9867 9868 9870
