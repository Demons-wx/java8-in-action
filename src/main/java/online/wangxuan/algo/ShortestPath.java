package online.wangxuan.algo;

import java.util.LinkedList;

/**
 * dijkstra 最短路径
 * @author xwangr
 * @date 2020/4/22
 */
public class ShortestPath {

    private class Graph {
        public LinkedList<Edge>[] adj; // 邻接表
        public int v;  // 顶点个数

        public Graph(int v) {
            this.v = v;
            this.adj = new LinkedList[v];
            for (int i = 0; i < v; i++) {
                this.adj[i] = new LinkedList<>();
            }
        }

        public void addEdge(int s, int t, int w) {  // 添加一条边
            this.adj[s].add(new Edge(s, t, w));
        }

        private class Edge {
            public int sid; // 边的起始顶点编号
            public int tid; // 边的终止顶点编号
            public int w; // 权重

            public Edge(int sid, int tid, int w) {
                this.sid = sid;
                this.tid = tid;
                this.w = w;
            }
        }
    }

    private class Vertex {
        public int id;  // 顶点编号ID
        public int dist;    // 从起始顶点到这个顶点的距离

        public Vertex(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }
    }

    private class PriorityQueue {
        private Vertex[] nodes;
        private int count;
        private int[] indexes;

        public PriorityQueue(int v) {
            this.nodes = new Vertex[v + 1];
            this.indexes = new int[v];
            this.count = 0;
        }

        public Vertex poll() {
            Vertex v = nodes[1];
            nodes[1] = nodes[count--];
            if (count > 1) {
                heapifyDown();
            }
            return v;
        }

        public void add(Vertex vertex) {
            nodes[++count] = vertex;
            indexes[vertex.id] = count;
            int i = count;
            heapifyUp(i);
        }

        public void update(Vertex vertex) {
            int pos = indexes[vertex.id];
            heapifyUp(pos);
        }

        private void heapifyUp(int pos) {
            while (pos / 2 > 0 && nodes[pos].dist < nodes[pos / 2].dist) {
                swap(pos, pos / 2);
                indexes[nodes[pos].id] = pos;
                indexes[nodes[pos / 2].id] = pos / 2;
                pos /= 2;
            }
        }

        private void heapifyDown() {
            int pos = 1;
            while (true) {
                int maxPos = pos;
                if (2 * pos <= count && nodes[2 * pos].dist < nodes[pos].dist)  maxPos = 2 * pos;
                if (2 * pos + 1 <= count && nodes[2 * pos + 1].dist < nodes[maxPos].dist) maxPos = 2 * pos + 1;
                if (maxPos == pos) return;
                swap(pos, maxPos);
                indexes[nodes[pos].id] = pos;
                indexes[nodes[maxPos].id] = maxPos;
                pos = maxPos;
            }
        }

        public boolean isEmpty() {
            return count <= 0;
        }

        private void swap(int i, int j) {
            Vertex tmp = nodes[i];
            nodes[i] = nodes[j];
            nodes[j] = tmp;
        }
    }

    private Graph graph = buildGraph();

    private Graph buildGraph() {
        Graph graph = new Graph(6);
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 4, 7);
        graph.addEdge(1, 2, 15);
        graph.addEdge(1, 3, 2);
        graph.addEdge(2, 5, 5);
        graph.addEdge(3, 2, 1);
        graph.addEdge(3, 5, 12);
        graph.addEdge(4, 5, 15);

        graph.addEdge(4, 1, 2);
        return graph;
    }

    /**
     * 这个算法最不容易让人理解的地方就是优先级队列里的某个顶点B pop出去以后，
     * 会不会以后会有某个还没有入队列的节点C 使经过节点C到B到源点的距离更近，
     * 实际这是不会的，因为优先级队列每次都是pop出当前离源点距离最近的点，
     * 假如节点C经过B使B到源点的距离更近，那么C点在优先级队列一定会比B先pop出去，
     * 然后更新B到源点的距离，想明白这一点，这个算法就很好理解了。
     *
     * @param s 起点
     * @param t 终点
     */
    public void dijkstra(int s, int t) {    // 从顶点s到顶点t的最短路径
        int[] predecessor = new int[graph.v];
        Vertex[] vertexes = new Vertex[graph.v];
        for (int i = 0; i < graph.v; i++) {
            vertexes[i] = new Vertex(i, Integer.MAX_VALUE);
        }

        PriorityQueue queue = new PriorityQueue(graph.v);    // 小顶堆
        boolean[] inQueue = new boolean[graph.v];    // 标记是否进入过队列
        vertexes[s].dist = 0;
        queue.add(vertexes[s]);
        inQueue[s] = true;

        while (!queue.isEmpty()) {
            Vertex minVertex = queue.poll();    // 取堆顶元素并删除
            if (minVertex.id == t) break;   // 最短路径产生了
            for (int i = 0; i < graph.adj[minVertex.id].size(); i++) {  // 遍历邻接表的节点
                Graph.Edge e = graph.adj[minVertex.id].get(i);  // 第i个与minVertex相连的边
                Vertex nextVertex = vertexes[e.tid];    // 第i个与minVertex相连的顶点
                if (minVertex.dist + e.w < nextVertex.dist) {   // 如果经过minVertex到nextVertex的路径权重小，需要插入/更新堆
                    nextVertex.dist = minVertex.dist + e.w;
                    predecessor[nextVertex.id] = minVertex.id;
                    if (inQueue[nextVertex.id]) {   // 更新
                        queue.update(nextVertex);
                    } else {    // 插入
                        queue.add(nextVertex);
                        inQueue[nextVertex.id] = true;
                    }
                }
            }
        }

        System.out.print(s);
        print(s, t, predecessor);
        System.out.println("\n" + vertexes[t].dist);
    }

    private void print(int s, int t, int[] predecessor) {
        if (s == t) return;
        print(s, predecessor[t], predecessor);
        System.out.print("->" + t);
    }

    public static void main(String[] args) {
        ShortestPath sp = new ShortestPath();
        sp.dijkstra(0, 5);
    }
}
