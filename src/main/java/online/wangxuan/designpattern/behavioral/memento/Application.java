package online.wangxuan.designpattern.behavioral.memento;

import java.util.Scanner;

/**
 * 备忘录模式
 * @author wangxuan
 * @date 2020/6/2 11:42 PM
 */

public class Application {

    public static void main(String[] args) {
        InputText inputText = new InputText();
        SnapshotHolder snapshotHolder = new SnapshotHolder();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String input = scanner.next();
            if (input.equals(":list")) {
                System.out.println(inputText.getText());
            } else if (input.equals(":undo")) {
                Snapshot snapshot = snapshotHolder.popSnapshot();
                inputText.restoreSnapshot(snapshot);
            } else {
                Snapshot snapshot = inputText.createSnapshot();
                snapshotHolder.pushSnapshot(snapshot);
                inputText.append(input);
            }
        }
    }
}
