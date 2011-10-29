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
        int edges = density*vertices*(vertices-1)/100;
        for(int i=0; i<edges;i++)
        {
            int x=(int) Math.random()%vertices;
            int y=(int) Math.random()%vertices;
            int weight = (int) Math.round(100 * Math.random());
            adj[x].add(y, weight);
        }
        //Check Connected ness
       }
    public void RandomGraph(){
      //  for(int i=0; i<vertices;i++)
            for(int j=0; j<vertices -4;j++ ){
                int weight = (int) Math.round(100 * Math.random());
                System.out.println(weight);
                adj[j].add((j+1)%vertices, weight);
            }
    }

    public void print(){
        for(int i=0; i<vertices;i++ )
            System.out.println(i + "  " + adj[i].toString());
    }

    private void addEdge(int vertex, int weight){
    }
    public int noOfVertices(){
        return vertices;
    }

}
