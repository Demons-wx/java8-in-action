package online.wangxuan.designpattern.metric;

import online.wangxuan.designpattern.metric.entity.RequestInfo;

import java.util.List;
import java.util.Map;

/**
 * @author wangxuan
 * @date 2020/5/17 10:40 PM
 */

public class RedisMetricStorage implements MetricStorage {

    @Override
    public void saveRequestInfo(RequestInfo requestInfo) {

    }

    @Override
    public List<RequestInfo> getRequestInfos(String apiName, long startTimeInMillis, long endTimeInMillis) {
        return null;
    }

    @Override
    public Map<String, List<RequestInfo>> getRequestInfos(long startTimeInMillis, long endTimeInMillis) {
        return null;
    }
}
