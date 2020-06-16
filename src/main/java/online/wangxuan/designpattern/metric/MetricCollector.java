package online.wangxuan.designpattern.metric;

import com.google.common.base.Strings;
import online.wangxuan.designpattern.metric.entity.RequestInfo;

/**
 * @author wangxuan
 * @date 2020/5/17 10:31 PM
 */

public class MetricCollector {

    private MetricStorage metricStorage;
    public MetricCollector(MetricStorage metricStorage) {
        this.metricStorage = metricStorage;
    }

    public void recordRequest(RequestInfo requestInfo) {
        if (requestInfo == null || Strings.isNullOrEmpty(requestInfo.getApiName())) {
            return;
        }

        metricStorage.saveRequestInfo(requestInfo);
    }
}
