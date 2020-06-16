package online.wangxuan.designpattern.structural.decorator.sample;

/**
 * @author wangxuan
 * @date 2020/6/14 11:29 AM
 */

public class DataInputStream extends InputStream {

    protected volatile InputStream inputStream;

    public DataInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    // 实现读取基本类型数据的接口...
}
