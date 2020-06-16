package online.wangxuan.designpattern.structural.bridge;

/**
 * @author wangxuan
 * @date 2020/6/14 10:16 AM
 */

public class UrgencyNotification extends Notification {

    public UrgencyNotification(MsgSender msgSender) {
        super(msgSender);
    }

    @Override
    public void notify(String message) {
        msgSender.send(message);
    }
}
