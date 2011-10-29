class Dsp {
    private int[] dist;
    private int[] edge;
    private PQ pq;
    Graph g;

    public Dsp(Graph G, int source){
        dist = new int[G.noOfVertices()];
        edge = new int[G.noOfVertices()];
        this.g = G;
        //pq = new ArrayQueue(dist);
        //pq = new BinomialQueue(dist);
        pq = new FibonacciQueue(dist);
        for (int v = 0; v < G.noOfVertices(); v++)
            dist[v] = Graph.INFINITY;
        dist[source] = 0;

        //relax vertices in order of distance from send
        pq.insert(source, dist[source]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for ( ListNode e : G.adj[v])
                relax(e, v);
        }
    }


    void relax(ListNode e, int v){
        int w = e.to();
        if (dist[w] > dist[v] + e.weight()) {
            dist[w] = dist[v] + e.weight();
            edge[w] = e.to();
            if (pq.contains(w))
                pq.decreaseKey(w, dist[w]);
            else
                pq.insert(w, dist[w]);
        }
    }

    public void print(){
        for(int i=0;i < g.noOfVertices(); i++)
            System.out.println(dist[i]);
    }
}