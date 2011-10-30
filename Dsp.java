class Dsp {
    private int[] dist;
    private int[] edge;
    private PQ pq; // Indexed Priority Queue
    Graph g;

    public Dsp(Graph G, int source, int scheme){
        //Dijkstra algo desecribed in project 
        dist = new int[G.noOfVertices()];
        edge = new int[G.noOfVertices()];
        this.g = G;
        //pq = new ArrayQueue(dist);
        //pq = new BinomialQueue(dist);
        //pq = new FibonacciQueue(dist);
        //
        initializeScheme(scheme); // initialize queue depending on scheme
        for (int v = 0; v < G.noOfVertices(); v++)
            dist[v] = Graph.INFINITY; // all node distances are inifinity initially.
        dist[source] = 0;

        //relax vertices in order of distance from send
        pq.insert(source, dist[source]);
        while (!pq.isEmpty()) {
            int v = pq.removeMin();
            for ( ListNode e : G.adj[v])
                relax(e, v);
        }
    }

    void initializeScheme(int s){
        if(s == 0)
            pq = new ArrayQueue(dist);
        if(s == 1)
            pq = new BinomialQueue(dist);
        if(s == 2)
            pq = new FibonacciQueue(dist);
    }


    void relax(ListNode e, int v){
        int w = e.to();
        if (dist[w] > dist[v] + e.weight()) { // check if its less than if it is relax it.
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
            System.out.print(dist[i]+ "  ");
          System.out.println("");
    }

    public int[] shortestPaths(){
        return dist;
    }
}