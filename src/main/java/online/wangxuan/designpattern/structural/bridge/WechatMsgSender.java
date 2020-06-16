package online.wangxuan.designpattern.structural.bridge;

import java.util.List;

/**
 * @author wangxuan
 * @date 2020/6/14 10:10 AM
 */

public class WechatMsgSender implements MsgSender {

    private List<String> wechatIds;

    public WechatMsgSender(List<String> wechatIds) {
        this.wechatIds = wechatIds;
    }

    @Override
    public void send(String message) {
        wechatIds.forEach(id -> System.out.println("To: " + id + ", message: " + message));
    }
}
