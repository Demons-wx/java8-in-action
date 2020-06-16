package online.wangxuan.designpattern.metric;

import online.wangxuan.designpattern.metric.entity.RequestStat;

import java.util.Map;

/**
 * @author wangxuan
 * @date 2020/5/17 11:01 PM
 */

public class ConsoleView implements StatView {

    @Override
    public void output(Map<String, RequestStat> requestStats, long startTimeInMillis, long endTimeInMillis) {

    }
}
