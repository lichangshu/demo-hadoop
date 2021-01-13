#!/bin/sh
set -xe

/usr/sbin/sshd

if [ ! -d '/root/.ssh/' ]; then
	ssh-keygen -f "/root/.ssh/id_rsa" -N ""
	cp /root/.ssh/id_rsa.pub /root/.ssh/authorized_keys
	chmod 600 /root/.ssh/*
fi

cd $HADOOP_ROOT
if [ ! -d "/tmp/hadoop-root/dfs/name/" ]; then
  ./bin/hdfs namenode -format
  set +ex
  {
    for k in $( seq 1 100 )
    do
        bin/hdfs dfs -D fs.defaultFS=hdfs://${HOSTNAME}:9000 -chmod 777 /
        if [ "$?" = "0" ]; then
            break
        fi
        sleep 1
    done
  } &

fi

set -xe

bin/hdfs --daemon start namenode -D fs.defaultFS=hdfs://${HOSTNAME}:9000 -D dfs.replication=1

bin/hdfs datanode -D fs.defaultFS=hdfs://${HOSTNAME}:9000 -D dfs.replication=1

# sbin/start-dfs.sh
# $HADOOP_ROOT/sbin/start-dfs.sh
# exec $@
