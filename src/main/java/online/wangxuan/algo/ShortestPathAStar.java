package online.wangxuan.algo;

import java.util.LinkedList;

/**
 * @author xwangr
 * @date 2020/4/29
 */
public class ShortestPathAStar {

    public static void main(String[] args) {
        ShortestPathAStar aStar = new ShortestPathAStar();
        aStar.aStar(0, 10);
    }

    public void aStar(int s, int t) {
        int[] predecessor = new int[graph.v];
        // 按照vertex的f值构造的小顶堆
        PriorityQueue queue = new PriorityQueue(graph.v);
        boolean[] inQueue = new boolean[graph.v];
        graph.vertexes[s].dist = 0;
        graph.vertexes[s].f = 0;
        queue.add(graph.vertexes[s]);
        inQueue[s] = true;

        while (!queue.isEmpty()) {
            Vertex minVertex = queue.poll();    // 弹出堆顶元素
            for (int i = 0; i < graph.adj[minVertex.id].size(); i++) {
                Graph.Edge e = graph.adj[minVertex.id].get(i);
                Vertex nextVertex = graph.vertexes[e.tid];
                if (minVertex.dist + e.w < nextVertex.dist) {
                    nextVertex.dist = minVertex.dist + e.w;
                    // f = s到nextVertex的dist + nextVertex到t的曼哈顿距离
                    nextVertex.f = nextVertex.dist + hManhattan(nextVertex, graph.vertexes[t]);
                    predecessor[nextVertex.id] = minVertex.id;

                    if (inQueue[nextVertex.id]) {
                        queue.update(nextVertex);
                    } else {
                        queue.add(nextVertex);
                        inQueue[nextVertex.id] = true;
                    }
                }
                if (nextVertex.id == t) {   // 只要到达t就可以结束while了
                    queue.clear();
                    break;
                }
            }
        }
        System.out.print(s);
        print(s, t, predecessor);
        System.out.println("\n" + graph.vertexes[t].dist);
    }

    private void print(int s, int t, int[] predecessor) {
        if (s == t) return;
        print(s, predecessor[t], predecessor);
        System.out.print("->" + t);
    }

    /**
     * 曼哈顿距离，两点之间横纵坐标的距离之和
     * @param v1    v1
     * @param v2    v2
     * @return      距离
     */
    private int hManhattan(Vertex v1, Vertex v2) {
        return Math.abs(v1.x - v2.x) + Math.abs(v1.y - v2.y);
    }

    private Graph graph = buildGraph();
    private Graph buildGraph() {
        Graph graph = new Graph(11);
        graph.addVertex(0, 320, 630);
        graph.addVertex(1, 300, 630);
        graph.addVertex(2, 280, 625);
        graph.addVertex(3, 270, 630);
        graph.addVertex(4, 320, 700);
        graph.addVertex(5, 360, 620);
        graph.addVertex(6, 320, 590);
        graph.addVertex(7, 370, 580);
        graph.addVertex(8, 350, 730);
        graph.addVertex(9, 390, 690);
        graph.addVertex(10, 400, 620);

        graph.addEdge(0, 1, 20);
        graph.addEdge(0, 4, 60);
        graph.addEdge(0, 5, 60);
        graph.addEdge(0, 6, 60);
        graph.addEdge(1, 2, 20);
        graph.addEdge(2, 3, 10);
        graph.addEdge(4, 8, 50);
        graph.addEdge(5, 8, 70);
        graph.addEdge(5, 9, 80);
        graph.addEdge(5, 10, 50);
        graph.addEdge(6, 7, 70);
        graph.addEdge(8, 9, 50);
        graph.addEdge(9, 10, 60);

        return graph;
    }

    private class Graph {
        public LinkedList<Edge>[] adj; // 邻接表
        public int v;  // 顶点个数
        public Vertex[] vertexes;

        public Graph(int v) {
            this.v = v;
            this.adj = new LinkedList[v];
            for (int i = 0; i < v; i++) {
                this.adj[i] = new LinkedList<>();
            }
            this.vertexes = new Vertex[v];
        }

        public void addVertex(int id, int x, int y) {
            vertexes[id] = new Vertex(id, x, y);
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
        public int id;      // 顶点编号ID
        public int dist;    // 从起始顶点到这个顶点的距离，也就是g(i)
        public int f;       // 新增：f(i) = g(i) + h(i)
        public int x, y;    // 新增：顶点在地图中的坐标

        public Vertex(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.f = Integer.MAX_VALUE;
            this.dist = Integer.MAX_VALUE;
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

        // 按照vertex的f值构造小顶堆
        private void heapifyDown() {
            int pos = 1;
            while (true) {
                int maxPos = pos;
                if (2 * pos <= count && nodes[2 * pos].f < nodes[pos].f) {
                    maxPos = 2 * pos;
                }
                if (2 * pos + 1 <= count && nodes[2 * pos + 1].f < nodes[maxPos].f) {
                    maxPos = 2 * pos + 1;
                }
                if (maxPos == pos) return;
                swap(maxPos, pos);
                indexes[nodes[pos].id] = pos;
                indexes[nodes[maxPos].id] = maxPos;
                pos = maxPos;
            }
        }

        // 按照vertex的f值构造小顶堆
        private void heapifyUp(int pos) {
            while (pos / 2 > 0 && nodes[pos / 2].f > nodes[pos].f) {
                swap(pos, pos / 2);
                indexes[nodes[pos].id] = pos;
                indexes[nodes[pos / 2].id] = pos / 2;
                pos /= 2;
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

        public void clear() {
            count = 0;
        }
    }
}
