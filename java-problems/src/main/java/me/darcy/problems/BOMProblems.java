package me.darcy.problems;

import org.apache.commons.io.input.BOMInputStream;

import java.io.*;

/**
 * https://www.cnblogs.com/anye-15068156823/p/8709229.html
 * 在Windows下用文本编辑器创建的文本文件，如果选择以UTF-8等Unicode格式保存，会在文件头（第一个字符）加入一个BOM标识。
 * 这个标识在Java读取文件的时候，不会被去掉，而且String.trim()也无法删除。如果用readLine()读取第一行存进String里面，这个String的length会比看到的大1，而且第一个字符就是这个BOM。
 *
 * 什么是BOM？
 * BOM = Byte Order Mark
 * BOM是Unicode规范中推荐的标记字节顺序的方法。比如说对于UTF-16，如果接收者收到的BOM是FEFF，表明这个字节流是Big-Endian的；如果收到FFFE，就表明这个字节流是Little-Endian的。
 * UTF-8不需要BOM来表明字节顺序，但可以用BOM来表明“我是UTF-8编码”。BOM的UTF-8编码是EF BB BF（用UltraEdit打开文本、切换到16进制可以看到）。所以如果接收者收到以EF BB BF开头的字节流，就知道这是UTF-8编码了。
 * 所有的BOM在C/C++/Java中都被处理为"\uFEFF"
 *
 * Wikipedia关于POM的说明介绍：
 * https://en.wikipedia.org/wiki/Byte_order_mark
 *
 * from: https://blog.csdn.net/ClementAD/article/details/47168573
 * 2. https://blog.csdn.net/u013805360/article/details/79536853
 * 3. https://blog.csdn.net/shibin1990_/article/details/51685510 通过文本编辑器解决这个问题;
 */
public class BOMProblems {

  public void solution1(String line) {
    if (line.startsWith("\uFEFF")) {
      //line = line.substring(1);
      line = line.replace("\uFEFF", "");
    }
  }

  public void solution2(String file) {
    BufferedReader reader = null;
    try {
      //reader = new BufferedReader(new FileReader(file));
      // 使用BOMInputStream自动去除UTF-8中的BOM！！！
      // 兼容不带BOM编码的文件;
      reader = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(file))));

      String str = null;
      //一次读入一行（非空），直到读入null为文件结束
      while ((str = reader.readLine()) != null) {

      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
