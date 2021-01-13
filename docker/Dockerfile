FROM  openjdk:8-jdk

ENV HADOOP_ROOT="/opt/hadoop-3.3.0"
RUN apt update && apt install -y openssh-server

RUN wget https://mirrors.bfsu.edu.cn/apache/hadoop/core/hadoop-3.3.0/hadoop-3.3.0.tar.gz -O /tmp/hadoop.tar.gz \
    && tar -xzvf  /tmp/hadoop.tar.gz -C /opt/

RUN mkdir /run/sshd

RUN echo "JAVA_HOME=$JAVA_HOME" >> $HADOOP_ROOT/etc/hadoop/hadoop-env.sh \
    && echo "HDFS_NAMENODE_USER=root" >> $HADOOP_ROOT/etc/hadoop/hadoop-env.sh \
    && echo "HDFS_DATANODE_USER=root" >> $HADOOP_ROOT/etc/hadoop/hadoop-env.sh \
    && echo "HDFS_SECONDARYNAMENODE_USER=root" >> $HADOOP_ROOT/etc/hadoop/hadoop-env.sh

ADD entrypoint.sh /entrypoint.sh

EXPOSE 22 9000 9864 9866 9867 9868 9870

ENTRYPOINT ["/entrypoint.sh"]
