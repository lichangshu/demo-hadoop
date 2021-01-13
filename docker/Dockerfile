FROM ubuntu:20.04

MAINTAINER charles <lcs.005@163.com>

ENV HADOOP_ROOT="/opt/hadoop-3.3.0"
ENV JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64"

RUN apt update && apt-get install -y --no-install-recommends \
           openssh-server \
           openjdk-8-jdk \
           curl

RUN curl -o /tmp/hadoop.tar.gz https://mirrors.bfsu.edu.cn/apache/hadoop/core/hadoop-3.3.0/hadoop-3.3.0.tar.gz
RUN tar -xzvf  /tmp/hadoop.tar.gz -C /opt/ --exclude=hadoop-3.3.0/share/doc

RUN mkdir /run/sshd

RUN echo "JAVA_HOME=$JAVA_HOME" >> $HADOOP_ROOT/etc/hadoop/hadoop-env.sh \
    && echo "HDFS_NAMENODE_USER=root" >> $HADOOP_ROOT/etc/hadoop/hadoop-env.sh \
    && echo "HDFS_DATANODE_USER=root" >> $HADOOP_ROOT/etc/hadoop/hadoop-env.sh \
    && echo "HDFS_SECONDARYNAMENODE_USER=root" >> $HADOOP_ROOT/etc/hadoop/hadoop-env.sh

ADD entrypoint.sh /entrypoint.sh

RUN chmod +x /entrypoint.sh \
    && apt-get clean \
    && rm -rf /tmp/* \
    && rm -rf /var/lib/apt/lists/*

EXPOSE 22 9000 9864 9866 9867 9868 9870

ENTRYPOINT ["/entrypoint.sh"]
