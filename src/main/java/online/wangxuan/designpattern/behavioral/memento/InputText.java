package online.wangxuan.designpattern.behavioral.memento;

/**
 * @author wangxuan
 * @date 2020/6/2 11:35 PM
 */

public class InputText {

    private StringBuilder text = new StringBuilder();

    public String getText() {
        return text.toString();
    }

    public void append(String input) {
        text.append(input);
    }

    public Snapshot createSnapshot() {
        return new Snapshot(text.toString());
    }

    public void restoreSnapshot(Snapshot snapshot) {
        this.text.replace(0, text.length(), snapshot.getText());
    }
}
