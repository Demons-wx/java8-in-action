package online.wangxuan.designpattern.behavioral.memento;

import java.util.Stack;

/**
 * @author wangxuan
 * @date 2020/6/2 11:40 PM
 */

public class SnapshotHolder {

    private Stack<Snapshot> snapshots = new Stack<>();

    public Snapshot popSnapshot() {
        return snapshots.pop();
    }

    public void pushSnapshot(Snapshot snapshot) {
        snapshots.push(snapshot);
    }
}
