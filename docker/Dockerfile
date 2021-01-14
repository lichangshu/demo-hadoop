## DOCKER hadoop v3.3.0-PseudoDistributed
FROM microwww/hadoop:v3.3.0-base

MAINTAINER charles <lcs.005@163.com>

RUN echo "JAVA_HOME=$JAVA_HOME" >> $HADOOP_HOME/etc/hadoop/hadoop-env.sh \
    && echo "HADOOP_SSH_OPTS=\"-p ${SSH_PORT}\"" >> $HADOOP_HOME/etc/hadoop/hadoop-env.sh \
    && echo "HDFS_NAMENODE_USER=root" >> $HADOOP_HOME/etc/hadoop/hadoop-env.sh \
    && echo "HDFS_DATANODE_USER=root" >> $HADOOP_HOME/etc/hadoop/hadoop-env.sh \
    && echo "HDFS_SECONDARYNAMENODE_USER=root" >> $HADOOP_HOME/etc/hadoop/hadoop-env.sh

ADD entrypoint.sh /entrypoint.sh

RUN chmod +x /entrypoint.sh \
    && apt-get clean \
    && rm -rf /tmp/* \
    && rm -rf /var/lib/apt/lists/*

ENTRYPOINT ["/entrypoint.sh"]