package cn.xingoo.jdk.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ScatterTest {
    public static void main(String[] args) throws IOException {
        ByteBuffer header   = ByteBuffer.allocate(10);
        ByteBuffer body     = ByteBuffer.allocate(100);

        ByteBuffer[] array = {header, body};

        String file = "/Users/xingoo/IdeaProjects/JDK-Learning/src/cn/xingoo/learn/nio/test.txt";
        // 创建文件
        RandomAccessFile accessFile = new RandomAccessFile(file,"rw");
        // 创建channel
        FileChannel inChannel = accessFile.getChannel();

        // read按照数组定义的顺序依次写入，写满第一个，才会写入第二个
        inChannel.read(array);
        // inChannel.write(array); channel的写入
        // 由于采用顺序填充模式，因此不适合动态消息体，如果存在消息头和消息体；
        // 最好消息头采用填充机制
        header.flip();
        System.out.println("header:");
        while(header.hasRemaining()){
            System.out.println((char)header.get());
        }

        body.flip();
        System.out.println("body:");
        while(body.hasRemaining()){
            System.out.println((char)body.get());
        }

        accessFile.close();
    }
}
