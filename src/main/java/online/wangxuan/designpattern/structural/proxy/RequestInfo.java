package online.wangxuan.designpattern.structural.proxy;

/**
 * @author wangxuan
 * @date 2020/5/14 10:01 PM
 */

public class RequestInfo {

    private String apiName;
    private long duration;
    private long requestTime;

    public RequestInfo(String login, long duration, long startTimestamp) {
        this.apiName = login;
        this.duration = duration;
        this.requestTime = startTimestamp;
    }

    @Override
    public String toString() {
        return "RequestInfo{" +
                "apiName='" + apiName + '\'' +
                ", duration=" + duration +
                ", requestTime=" + requestTime +
                '}';
    }
}
