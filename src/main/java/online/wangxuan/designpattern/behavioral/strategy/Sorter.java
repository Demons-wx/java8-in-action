package online.wangxuan.designpattern.behavioral.strategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxuan
 * @date 2020/5/25 10:12 PM
 */

public class Sorter {

    // 1 GB = 1000 * 1000 * 1000 Bytes
    private static final long GB = 1000 * 1000 * 1000;
    private static final List<AlgRange> algs = new ArrayList<>();
    static {
        algs.add(new AlgRange(0L, 6 * GB, SortAlgFactory.getSortAlg("QuickSort")));
        algs.add(new AlgRange(6 * GB, 10 * GB, SortAlgFactory.getSortAlg("ExternalSort")));
        algs.add(new AlgRange(10 * GB, 100 * GB, SortAlgFactory.getSortAlg("ConcurrentExternalSort")));
        algs.add(new AlgRange(100 * GB, Long.MAX_VALUE, SortAlgFactory.getSortAlg("MapReduceSort")));
    }

    public void sortFile(String filePath) {
        File file = new File(filePath);
        long fileSize = file.length();
        ISortAlg sortAlg = null;
        for (AlgRange algRange : algs) {
            if (algRange.inRange(fileSize)) {
                sortAlg = algRange.getAlg();
                break;
            }
        }
        if (sortAlg != null) {
            sortAlg.sort(filePath);
        }
    }

    private static class AlgRange {
        private long start;
        private long end;
        private ISortAlg alg;

        public AlgRange(long start, long end, ISortAlg alg) {
            this.start = start;
            this.end = end;
            this.alg = alg;
        }

        public boolean inRange(long size) {
            return size >= start && size <= end;
        }

        public ISortAlg getAlg() {
            return alg;
        }
    }
}
