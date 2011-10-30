class Graph {
    public static final Integer INFINITY = Integer.MAX_VALUE;
    private int vertices;
    public LinkedList[] adj;

    public Graph(int vertices){
        this.vertices = vertices;
        //initialize adjancency list.
        adj = new LinkedList[vertices];
        for(int i=0; i<vertices;i++ )
            adj[i] = new LinkedList();
    }

     // clear all existing edges.
    public void clear(){
        adj = new LinkedList[vertices];
        for(int i=0; i<vertices;i++ )
            adj[i] = new LinkedList();
    }


    /**
     * Generate random graph as described in assignment.
     * */
    public void RandomGraph(int density){
        int edges = density*vertices*(vertices-1)/100 - vertices;
        RandomGraph();
        int i=0;
        while(i<edges)
        {
            int x = (int) Math.round((vertices-1) * Math.random());//random vertex from
            int y = (int) Math.round((vertices-1) * Math.random());//random vertex to
            int weight = (int) Math.round(1 + 999 * Math.random());// random weight
            //adj[x].add(y, weight);
            if(addEdge(x,y, weight)) // add edge only if not present
                i++;
        }
        //Check Connected ness
        if(!checkConnectedness()){
            clear();
            System.out.println("xx");
            RandomGraph(density); // initialize again
        }
    }

    public void RandomGraph(){
        //  for(int i=0; i<vertices;i++)
        for(int j=0; j<vertices;j++ ){
            int weight = (int) Math.round(1 + 999 * Math.random());
            //System.out.println(weight);
            adj[j].add((j+1)%vertices, weight);
        }
    }

    public void print(){
        for(int i=0; i<vertices;i++ )
            System.out.println(i + "  " + adj[i].toString());
    }

    public boolean addEdge(int v1, int v2,  int weight){
        for(ListNode e: this.adj[v1])
            //prevent edge to node itself and an edge between v1 and v2 already exists.
            if((e.to() == v2) || (e.to() == v1) || (v1 == v2))
                return false;
        adj[v1].add(v2, weight);
        return true;
    }


    public int noOfVertices(){
        return vertices;
    }

    /**
     * Run for one vertex for one scheme and return
     * distance.
     */
    public  int[] calculateDspFrom(int vertex, int scheme){
        Dsp d = new Dsp(this, vertex, scheme);
        return d.shortestPaths();
    }

   /**
    * Run dijkstra for all vertices and return distance
    * matrix.
    */
    public int[][] calculateDspForAll(int scheme){
        int[][] d = new int[vertices][vertices];
        for(int i=0; i<vertices;i++)
            d[i] = calculateDspFrom(i, scheme);
        return d;
    }

   /**
    * Profile dijkstra for all vertices and return
    * time taken.
    */
    public long profileDspForAll(int scheme){
        long start, stop;
        //start profiling
        start = System.currentTimeMillis();
        for(int i=0; i<vertices;i++)
            calculateDspFrom(i, scheme);
        stop = System.currentTimeMillis();
        return stop - start;
    }

    /**
     * Profile for one vertex for one scheme and return
     * time taken.
     */
    public long profileDspForOne(int scheme){
        long start, stop;
        start = System.currentTimeMillis();
        calculateDspFrom(0, scheme);
        stop = System.currentTimeMillis();
        return stop - start;
    }

    /**
     * Check whether graph is strongly connected by running
     * dijkstra on one vertex as described in assignment.
     */
    public boolean checkConnectedness(){
        int[] d;
        d = calculateDspFrom(1, 1);
        for(int i =0;i<vertices;i++)
           if( d[i] == Graph.INFINITY)
               return false;
        return true;
    }


    public static void main(String[] args) {
        for(int i=0; i<100;i++){
            int x = (int) Math.round(1 + 999 * Math.random());
            System.out.println(x);
        }
        Graph G;
        G = new Graph(500);

        G.RandomGraph(100);
        System.out.println("G Done!");
        System.out.println(G.profileDspForAll(0));
    }
}
