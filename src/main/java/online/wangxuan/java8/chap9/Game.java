package online.wangxuan.java8.chap9;

import java.util.Arrays;
import java.util.List;

/**
 * @author wangxuan
 * @date 2019/1/13 6:28 PM
 */

public class Game {

    public static void main(String[] args) {
        List<Resizable> resizableShapes = Arrays.asList(new Square(), new Rectangle(), new Ellipse());
        Utils.paint(resizableShapes);
    }
}
