package com.darcy.ch5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * Author by darcy
 * Date on 17-6-1 下午9:18.
 * Description:
 */
public class BufferOpDemo {
    public void testGetSet() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty In Action!", utf8);
        System.out.println((char) buf.getByte(0));
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();

        buf.setByte(0, (byte) 'B');
        System.out.println((char) buf.getByte(0));
        System.out.println(readerIndex == buf.readerIndex()); // true
        System.out.println(writerIndex == buf.writerIndex()); // true

    }

    public void testReadWrite() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty In Action!", utf8);
        System.out.println((char) buf.readByte());
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();

        buf.writeByte((byte) '?');
        System.out.println(readerIndex == buf.readerIndex()); // true
        System.out.println(writerIndex == buf.writerIndex()); // false

    }

    public static void main(String[] args) {
        BufferOpDemo demo = new BufferOpDemo();
        demo.testGetSet();

        System.out.println();

        demo.testReadWrite();
    }
}
