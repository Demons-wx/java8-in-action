package online.wangxuan.designpattern.structural.bridge;

/**
 * @author wangxuan
 * @date 2020/6/14 10:17 AM
 */

public class NormalNotification extends Notification {
    public NormalNotification(MsgSender msgSender) {
        super(msgSender);
    }

    @Override
    public void notify(String message) {
        msgSender.send(message);
    }
}
