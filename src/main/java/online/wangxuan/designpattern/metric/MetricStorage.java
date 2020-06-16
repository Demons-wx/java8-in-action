package online.wangxuan.designpattern.metric;

import online.wangxuan.designpattern.metric.entity.RequestInfo;

import java.util.List;
import java.util.Map;

/**
 * @author wangxuan
 * @date 2020/5/17 10:31 PM
 */

public interface MetricStorage {
    void saveRequestInfo(RequestInfo requestInfo);
    List<RequestInfo> getRequestInfos(String apiName, long startTimeInMillis, long endTimeInMillis);
    Map<String, List<RequestInfo>> getRequestInfos(long startTimeInMillis, long endTimeInMillis);

}
