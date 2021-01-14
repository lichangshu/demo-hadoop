package com.demo.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

public class ClientTest {

    @Test
    public void testConnection() throws IOException, URISyntaxException, InterruptedException {
        Configuration conf = new Configuration();
        Path path = new Path("/demo");
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.1.67:9000"), conf, "demo");
        fs.mkdirs(path);
        FileStatus st = fs.getFileStatus(path);
        Assert.assertEquals("demo", st.getOwner());
        fs.close();
    }

    @Test
    public void testWrite() throws IOException, URISyntaxException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("dfs.client.use.datanode.hostname", "true");
        Path path = new Path("/demo/file1.test");
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.1.67:9000"), conf, "demo");
        FSDataOutputStream out = fs.create(path);
        out.write("this is a test ! \n".getBytes());
        out.flush();
        fs.close();
    }

    @Test
    public void testRead() throws IOException, URISyntaxException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("dfs.client.use.datanode.hostname", "true");
        Path path = new Path("/demo/file1.test");
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.1.67:9000"), conf, "demo");
        int size = 1 * 1024 * 1024;
        FSDataInputStream open = fs.open(path, size);
        ByteBuffer bf = ByteBuffer.allocate(size);
        byte[] bytes = new byte[size];
        while (true) {
            bf.clear();
            int read = open.read(bf);
            if (read == 0) {
                Thread.sleep(100);
            } else if (read < 0) {
                break;
            }
            bf.rewind();
            bf.get(bytes);
            System.out.println(new String(bytes));
        }
    }
}
