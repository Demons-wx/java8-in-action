package online.wangxuan.algo;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author xwangr
 * @date 2020/3/23
 */
public class Graph {

    // 顶点个数
    private int v;
    // 邻接表 (还有一种是邻接矩阵, 用数组实现)
    private LinkedList<Integer>[] adj;

    public Graph(int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    // 无向图的一条边存两次
    public void addEdge(int s, int t) {
        adj[s].add(t);
        adj[t].add(s);
    }

    /**
     * 广度优先
     * @param s 起点
     * @param t 终点
     */
    public void bfs(int s, int t) {
        if (s == t) return;

        // 是用来记录已经被访问的顶点，用来避免顶点被重复访问。
        // 如果顶点 q 被访问，那相应的 visited[q]会被设置为 true
        boolean[] visited = new boolean[v];
        visited[s] = true;

        // 是一个队列，用来存储已经被访问、但相连的顶点还没有被访问的顶点。
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);

        // 用来记录搜索路径。
        // 当我们从顶点 s 开始，广度优先搜索到顶点 t 后，prev 数组中存储的就是搜索的路径。
        int[] prev = new int[v];
        for (int i = 0; i < v; i++) {
            prev[i] = -1;
        }

        while (queue.size() != 0) {
            int w = queue.poll();
            for (int i = 0; i < adj[w].size(); i++) {
                int q = adj[w].get(i);
                if (!visited[q]) {
                    prev[q] = w;
                    if (q == t) {
                        print(prev, s, t);
                        return;
                    }
                    visited[q] = true;
                    queue.add(q);
                }
            }
        }
    }

    boolean found = false;
    public void dfs(int s, int t) {
        if (s == t) return;
        boolean[] visited = new boolean[v];
        int[] prev = new int[v];
        for (int i = 0; i < v; i++) {
            prev[i] = -1;
        }

        dfsRecursive(s, t, visited, prev);
        print(prev, s, t);
    }

    private void dfsRecursive(int w, int t, boolean[] visited, int[] prev) {
        if (found) return;
        visited[w] = true;
        if (w == t) {
            found = true;
            return;
        }

        for (int i = 0; i < adj[w].size(); i++) {
            int q = adj[w].get(i);
            if (!visited[q]) {
                prev[q] = w;
                dfsRecursive(q, t, visited, prev);
            }
        }
    }

    private void print(int[] prev, int s, int t) {
        if (prev[t] != -1 && t != s) {
            print(prev, s, prev[t]);
        }
        System.out.print(t + " ");
    }

    public static void main(String[] args) {
        Graph graph = new Graph(8);

        graph.addEdge(0, 1);
        graph.addEdge(0, 3);
        graph.addEdge(1, 2);
        graph.addEdge(1, 4);
        graph.addEdge(2, 5);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(4, 6);
        graph.addEdge(6, 7);

        graph.bfs(0, 7);
        System.out.println();
        graph.dfs(0, 7);
    }
}
