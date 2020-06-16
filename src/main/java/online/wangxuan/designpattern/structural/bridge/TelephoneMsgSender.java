package online.wangxuan.designpattern.structural.bridge;

import java.util.List;

/**
 * @author wangxuan
 * @date 2020/6/14 10:06 AM
 */

public class TelephoneMsgSender implements MsgSender {

    private List<String> telephones;

    public TelephoneMsgSender(List<String> telephones) {
        this.telephones = telephones;
    }

    @Override
    public void send(String message) {
        telephones.forEach(telephone -> System.out.println("To: " + telephone + ", message: " + message));
    }
}
