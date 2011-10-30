class Graph {
    public static final Integer INFINITY = Integer.MAX_VALUE;
    private int vertices;
    public LinkedList[] adj;

    public Graph(int vertices){
        this.vertices = vertices;
        adj = new LinkedList[vertices];
        for(int i=0; i<vertices;i++ )
            adj[i] = new LinkedList();
    }

    public void RandomGraph(int density){
        int edges = density*vertices*(vertices-1)/100 - vertices;
        RandomGraph();
        int i=0;
        while(i<edges)
        {
            int x = (int) Math.round((vertices-1) * Math.random());
            int y = (int) Math.round((vertices-1) * Math.random());
            int weight = (int) Math.round(1 + 999 * Math.random());
            //adj[x].add(y, weight);
            if(addEdge(x,y, weight))
                i++;
        }
        //Check Connected ness
        //checkConnectedness();
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


    public  int[] calculateDspFrom(int vertex, int scheme){
        Dsp d = new Dsp(this, vertex, scheme);
        return d.shortestPaths();
    }

    public int[][] calculateDspForAll(int scheme){
        int[][] d = new int[vertices][vertices];
        for(int i=0; i<vertices;i++)
            d[i] = calculateDspFrom(i, scheme);
        return d;
    }

    public void printDspMatrix(){
    }


    public long profileDspForAll(int scheme){
        long start, stop;
        start = System.currentTimeMillis();
        for(int i=0; i<vertices;i++)
            calculateDspFrom(i, scheme);
        stop = System.currentTimeMillis();
        return stop - start;
    }

    public long profileDspForOne(int scheme){
        long start, stop;
        start = System.currentTimeMillis();
        calculateDspFrom(0, scheme);
        stop = System.currentTimeMillis();
        return stop - start;
    }

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
        G = new Graph(100);

        G.RandomGraph(20);
        System.out.println("G Done!");
        System.out.println(G.profileDspForOne(0));
    }
}
