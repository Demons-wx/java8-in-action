package online.wangxuan.designpattern.structural.decorator.sample;

/**
 * @author wangxuan
 * @date 2020/6/14 11:28 AM
 */

public class BufferedInputStream extends InputStream {

    protected volatile InputStream in;

    public BufferedInputStream(InputStream in) {
        this.in = in;
    }

    // 实现基于缓存的读数据接口...
}
