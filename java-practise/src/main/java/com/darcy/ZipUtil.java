package com.darcy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ZipUtil {
    // private static final Logger log = LoggerFactory.getLogger(ZipUtil.class);

    public static byte[] compress(byte[] input) throws RuntimeException {
        Deflater compress = new Deflater();
        byte[] output;
        compress.reset();
        compress.setInput(input);
        compress.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
        try {
            byte[] buf = new byte[1024];
            while (!compress.finished()) {
                int i = compress.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            compress.end();
            try {
                bos.close();
            } catch (IOException e) {
                System.err.println("failed to close stream");
            }
        }
        return output;
    }

    public static byte[] decompress(byte[] input) throws RuntimeException{
        Inflater inflater = new Inflater();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(input.length);
        try {
            inflater.setInput(input);
            byte[] buffer = new byte[1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                if (count == 0) {
                    break;
                }
                outputStream.write(buffer, 0, count);
            }
            byte[] result = outputStream.toByteArray();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                System.err.println("failed to close stream");
            }
            inflater.end();
        }
    }

    public static void main(String[] args) {
        byte[] originData = "public static void main(String[] args)".getBytes(StandardCharsets.UTF_8);
        while (true) {
            try {
                byte[] compressData = compress(originData);
                byte[] decompressData = decompress(compressData);
                System.out.println(new String(decompressData));
                Thread.sleep(10);
            } catch (Exception exception) {
                System.err.println(exception.getMessage());
            }
        }
    }
}
