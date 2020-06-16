package online.wangxuan.designpattern.structural.decorator.sample;

import java.io.*;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.InputStream;

/**
 * @author wangxuan
 * @date 2020/6/14 11:31 AM
 */

public class Application {

    public static void main(String[] args) throws Exception {
        InputStream in = new FileInputStream("");
        InputStream bin = new BufferedInputStream(in);
        DataInputStream din = new DataInputStream(bin);
        int data = din.readInt();
    }
}
