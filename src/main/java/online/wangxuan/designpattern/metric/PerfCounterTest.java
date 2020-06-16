package online.wangxuan.designpattern.metric;

import online.wangxuan.designpattern.metric.entity.RequestInfo;

import java.util.concurrent.TimeUnit;

/**
 * @author wangxuan
 * @date 2020/5/17 11:05 PM
 */

public class PerfCounterTest {

    public static void main(String[] args) throws InterruptedException {
        MetricStorage metricStorage = new RedisMetricStorage();
        Aggregator aggregator = new Aggregator();

        // 定时触发统计并显示到终端
        ConsoleView consoleView = new ConsoleView();
        ConsoleReporter consoleReporter = new ConsoleReporter(metricStorage, aggregator, consoleView);
        consoleReporter.startRepeatedReport(60, 60);

        // 定时触发统计并输出到邮件

        // 收集接口访问数据
        MetricCollector metricCollector = new MetricCollector(metricStorage);
        metricCollector.recordRequest(new RequestInfo("register", 123, 123));
        metricCollector.recordRequest(new RequestInfo("register", 1234, 1234));
        metricCollector.recordRequest(new RequestInfo("register", 456, 456));
        metricCollector.recordRequest(new RequestInfo("login", 123, 123));
        metricCollector.recordRequest(new RequestInfo("login", 456, 456));

        TimeUnit.MINUTES.sleep(10);
    }
}
