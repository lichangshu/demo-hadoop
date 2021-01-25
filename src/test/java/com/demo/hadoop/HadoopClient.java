package com.demo.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class HadoopClient {
    private static final Logger logger = LoggerFactory.getLogger(HadoopClient.class);
    protected Configuration conf;
    protected Job job;

    @Before
    public void init() throws IOException {
        conf = new Configuration();
        conf.set("dfs.client.use.datanode.hostname", "true");
        conf.set("fs.defaultFS", "hdfs://192.168.1.67:9000");
        job = Job.getInstance(conf);
    }

    public void setOutput(Path path, boolean delete) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        if (delete) {
            fs.delete(path, true);
        }
        FileOutputFormat.setOutputPath(job, path);
    }

    public void printHeader(Path path, int line) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        FSDataInputStream st = fs.open(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(st));
        for (int i = 0; i < line; i++) {
            String ln = reader.readLine();
            if (ln == null) break;
            logger.info("  | {}", ln);
        }
    }
}
