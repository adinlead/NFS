package cc.itez.nfs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {

    /**
     * 将InputStream的内容复制到OutputStream中。
     * 使用默认缓冲区大小8024字节。
     *
     * @param input  - 要复制的InputStream
     * @param output - 目标Stream
     * @return 复制的字节数（总长度）
     * @throws IOException - 如果发生错误将原样抛出
     */
    public static long copy(InputStream input, OutputStream output) throws IOException {
        return copy(input, output, 8024);
    }

    /**
     * 将InputStream的内容复制到OutputStream中。
     *
     * @param input      - 要复制的InputStream
     * @param output     - 目标Stream
     * @param bufferSize - 要使用的缓冲区大小，必须大于0,如果小于8则使用8
     * @return 复制的字节数
     * @throws IOException - 如果发生错误将原样抛出
     */
    public static long copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
        bufferSize = Math.max(8, bufferSize);
        final byte[] buffer = new byte[bufferSize];
        int n;
        long count = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
}
