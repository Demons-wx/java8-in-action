package online.wangxuan.designpattern.structural.bridge;

/**
 * @author wangxuan
 * @date 2020/6/14 10:13 AM
 */

public class ServerNotification extends Notification {

    public ServerNotification(MsgSender msgSender) {
        super(msgSender);
    }

    @Override
    public void notify(String message) {
        msgSender.send(message);
    }
}
