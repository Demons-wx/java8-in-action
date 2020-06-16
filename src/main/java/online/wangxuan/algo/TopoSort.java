package online.wangxuan.algo;

import java.util.LinkedList;

/**
 * 拓扑排序
 * @author xwangr
 * @date 2020/4/24
 */
public class TopoSort {

    private class Graph {
        private int v;
        private LinkedList<Integer>[] adj;

        public Graph(int v) {
            this.v = v;
            adj = new LinkedList[v];
            for (int i = 0; i < v; i++) {
                adj[i] = new LinkedList<>();
            }
        }

        public void addEdge(int s, int t) {
            adj[s].add(t);
        }
    }

    private Graph graph = buildGraph();
    private Graph buildGraph() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        return graph;
    }

    public void topoSortByKahn() {
        int[] inDegree = new int[graph.v];  // 统计每个顶点的入度
        for (int i = 0; i < graph.v; i++) {
            for (int j = 0; j < graph.adj[i].size(); j++) {
                int w = graph.adj[i].get(j);    // i -> w
                inDegree[w]++;
            }
        }

        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < graph.v; i++) {
            if (inDegree[i] == 0) queue.add(i);
        }

        while (!queue.isEmpty()) {
            int i = queue.remove();
            System.out.print("->" + i);
            for (int j = 0; j < graph.adj[i].size(); j++) {
                int k = graph.adj[i].get(j);
                inDegree[k]--;
                if (inDegree[k] == 0) queue.add(k);
            }
        }
    }

    public void topoSortByDFS() {
        // 构建逆邻接表 s->t表示s依赖于t，t先于s
        LinkedList<Integer>[] inverseAdj = new LinkedList[graph.v];
        for (int i = 0; i < graph.v; i++) {
            inverseAdj[i] = new LinkedList<>();
        }
        for (int i = 0; i < graph.v; i++) {
            for (int j = 0; j < graph.adj[i].size(); j++) {
                int w = graph.adj[i].get(j);
                inverseAdj[w].add(i);
            }
        }

        boolean[] visited = new boolean[graph.v];
        // 深度优先遍历图
        for (int i = 0; i < graph.v; i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(i, inverseAdj, visited);
            }
        }
    }

    private void dfs(int vertex, LinkedList<Integer>[] inverseAdj, boolean[] visited) {
        for (int i = 0; i < inverseAdj[vertex].size(); i++) {
            int w = inverseAdj[vertex].get(i);
            if (visited[w]) continue;
            visited[w] = true;
            dfs(w, inverseAdj, visited);
        }
        // 先把vertex这个顶点可达的所有顶点都打印出来之后，再打印它自己
        System.out.print("->" + vertex);
    }

    public static void main(String[] args) {
        TopoSort topoSort = new TopoSort();
//        topoSort.topoSortByKahn();
        topoSort.topoSortByDFS();
    }
}
