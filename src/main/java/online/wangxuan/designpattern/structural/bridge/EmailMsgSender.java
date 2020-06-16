package online.wangxuan.designpattern.structural.bridge;

import java.util.List;

/**
 * @author wangxuan
 * @date 2020/6/14 10:09 AM
 */

public class EmailMsgSender implements MsgSender {

    private List<String> emails;

    public EmailMsgSender(List<String> emails) {
        this.emails = emails;
    }

    @Override
    public void send(String message) {
        emails.forEach(email -> System.out.println("To: " + email + ", message: " + message));
    }
}
