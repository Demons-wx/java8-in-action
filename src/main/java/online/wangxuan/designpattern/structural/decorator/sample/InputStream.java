package online.wangxuan.designpattern.structural.decorator.sample;

import java.io.IOException;

/**
 * @author wangxuan
 * @date 2020/6/14 11:22 AM
 */

public abstract class InputStream {

    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    private int read(byte[] b, int off, int length) throws IOException {
        // ...
        return 0;
    }

    public long skip(long n) throws IOException {
        // ...
        return 0L;
    }

    public int available() throws IOException {
        return 0;
    }

    public void close() throws IOException {

    }

    public synchronized void mark(int readLimit) {}

    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not support");
    }

    public boolean markSupported() {
        return false;
    }

}
