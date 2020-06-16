package online.wangxuan.designpattern.metric;

import online.wangxuan.designpattern.metric.entity.RequestInfo;
import online.wangxuan.designpattern.metric.entity.RequestStat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangxuan
 * @date 2020/5/17 10:41 PM
 */

public class Aggregator {

    public Map<String, RequestStat> aggregator(Map<String, List<RequestInfo>> requestInfos,
                                               long durationInMillis) {
        Map<String, RequestStat> requestStats = new HashMap<>();
        for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
            String apiName = entry.getKey();
            List<RequestInfo> requestInfoPerApi = entry.getValue();
            RequestStat requestStat = doAggregate(requestInfoPerApi, durationInMillis);
            requestStats.put(apiName, requestStat);
        }
        return requestStats;
    }

    private RequestStat doAggregate(List<RequestInfo> requestInfos,
                                    long durationInMillis) {
        List<Double> respTimes = new ArrayList<>();
        for (RequestInfo requestInfo : requestInfos) {
            double respTime = requestInfo.getResponseTime();
            respTimes.add(respTime);
        }
        RequestStat requestStat = new RequestStat();
        requestStat.setMaxResponseTime(max(respTimes));
        requestStat.setMinResponseTime(min(respTimes));
        requestStat.setAvgResponseTime(avg(respTimes));
        requestStat.setTps(tps(respTimes.size(), durationInMillis / 1_000));
        requestStat.setP99ResponseTime(percentile99(respTimes));
        requestStat.setP999ResponseTime(percentile999(respTimes));
        requestStat.setCount(respTimes.size());

        return requestStat;
    }

    private double percentile999(List<Double> dataSet) {
        return 0;
    }

    private double percentile99(List<Double> dataSet) {
        return 0;
    }

    private double max(List<Double> dataSet) {
        return 0.0;
    }

    private double min(List<Double> dataSet) {
        return 0.0;
    }

    private double avg(List<Double> dataSet) {
        return 0.0;
    }

    private long tps(int count, double duration) {
        return 1L;
    }

}
