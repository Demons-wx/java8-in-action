package online.wangxuan.designpattern.behavioral.memento;

/**
 * @author wangxuan
 * @date 2020/6/2 11:38 PM
 */

public class Snapshot {

    private String text;

    public Snapshot(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
