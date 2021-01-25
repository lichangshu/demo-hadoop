package com.demo.hadoop;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.junit.Test;

import java.io.IOException;
import java.util.StringTokenizer;

public class MapReduceTest extends HadoopClient { // WordCount

    public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private final static Text word = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            StringTokenizer tokenizer = new StringTokenizer(line, "\t .,;=<>{}/\"");
            while (tokenizer.hasMoreTokens()) {
                word.set(tokenizer.nextToken());
                context.write(word, one);  // 会被序列化. 所以可以使用同一个对象
            }
        }
    }

    public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {
        IntWritable count = new IntWritable();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable next : values) {
                sum += next.get();
            }
            count.set(sum);
            context.write(key, count);
        }
    }

    @Test
    public void testWordCount() throws Exception {
        job.setJobName("WordCount");
        job.setMapperClass(MapReduceTest.Map.class);
        job.setCombinerClass(Reduce.class);
        job.setReducerClass(Reduce.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job, new Path("/etc/hadoop"));
        Path out = new Path("/result");
        this.setOutput(out, true);

        boolean success = job.waitForCompletion(true);
        if (success) {
            printHeader(new Path(out, "part-r-00000"), 100);
        }
        System.exit(success ? 0 : 1);
    }

}
