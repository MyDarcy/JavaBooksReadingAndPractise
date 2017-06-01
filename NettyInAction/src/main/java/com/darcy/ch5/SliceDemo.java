package com.darcy.ch5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * Author by darcy
 * Date on 17-6-1 下午9:06.
 * Description:
 */
public class SliceDemo {

    public void testSlice() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty In Action!", utf8);
        ByteBuf slice = buf.slice(0, 10);

        System.out.println(slice.toString());
        buf.setByte(0, (byte) 'J');
        System.out.println(buf.getByte(0) == slice.getByte(0));
    }

    public void testCopy() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty In Action!", utf8);
        ByteBuf copy = buf.copy(0, 10);

        System.out.println(copy.toString());
        buf.setByte(0, (byte) 'J');
        System.out.println(buf.getByte(0) == copy.getByte(0));;
    }



    public static void main(String[] args) {
        SliceDemo sliceDemo = new SliceDemo();
        sliceDemo.testSlice();
        sliceDemo.testCopy();
    }
}
