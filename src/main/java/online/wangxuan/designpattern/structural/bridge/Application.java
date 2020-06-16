package online.wangxuan.designpattern.structural.bridge;

import java.util.Arrays;

/**
 * @author wangxuan
 * @date 2020/6/14 10:19 AM
 */

public class Application {

    public static void main(String[] args) {
        MsgSender msgSender = new TelephoneMsgSender(Arrays.asList("13800001111", "13811110000"));
        Notification notification = new ServerNotification(msgSender);
        notification.notify("系统挂了!");
    }
}
