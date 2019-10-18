package me.darcy.compress;

import org.anarres.lzo.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class LzoUtils {

  public static byte[] compress(byte srcBytes[]) throws IOException {
    LzoCompressor compressor = LzoLibrary.getInstance().newCompressor(
        LzoAlgorithm.LZO1X, null);

    ByteArrayOutputStream os = new ByteArrayOutputStream();
    LzoOutputStream cs = new LzoOutputStream(os, compressor);
    cs.write(srcBytes);
    cs.close();

    return os.toByteArray();
  }

  public static byte[] uncompress(byte[] bytes) throws IOException {
    LzoDecompressor decompressor = LzoLibrary.getInstance()
        .newDecompressor(LzoAlgorithm.LZO1X, null);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ByteArrayInputStream is = new ByteArrayInputStream(bytes);
    LzoInputStream us = new LzoInputStream(is, decompressor);
    int count;
    byte[] buffer = new byte[2048];
    while ((count = us.read(buffer)) != -1) {
      baos.write(buffer, 0, count);
    }
    return baos.toByteArray();
  }

}
