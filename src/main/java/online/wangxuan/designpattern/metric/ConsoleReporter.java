package online.wangxuan.designpattern.metric;

import online.wangxuan.designpattern.metric.entity.RequestInfo;
import online.wangxuan.designpattern.metric.entity.RequestStat;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author wangxuan
 * @date 2020/5/17 11:04 PM
 */

public class ConsoleReporter {

    private MetricStorage metricStorage;
    private Aggregator aggregator;
    private StatView statView;
    private ScheduledExecutorService executor;

    public ConsoleReporter(MetricStorage metricStorage,
                           Aggregator aggregator,
                           StatView statView) {
        this.metricStorage = metricStorage;
        this.aggregator = aggregator;
        this.statView = statView;
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }


    public void startRepeatedReport(int periodInSeconds, int durationInSeconds) {
        executor.scheduleAtFixedRate(() -> {
            long durationInMillis = durationInSeconds * 1_000;
            long endTimeInMillis = System.currentTimeMillis();
            long startTimeInMillis = endTimeInMillis - durationInMillis;
            Map<String, List<RequestInfo>> requestInfos =
                    metricStorage.getRequestInfos(startTimeInMillis, endTimeInMillis);
            Map<String, RequestStat> requestStats = aggregator.aggregator(requestInfos, durationInMillis);
            statView.output(requestStats, startTimeInMillis, endTimeInMillis);
        }, 0L, periodInSeconds, TimeUnit.SECONDS);
    }
}
