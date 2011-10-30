class ArrayQueue implements PQ{
    private int[] dist; //actual distance array.
    private boolean[] visited; //check for visited nodes.
    private int size;

    public ArrayQueue(int[] dist){
        this.size  = dist.length;
        //this.dist = new int[size];
        this.dist = dist;
        this.visited = new boolean[size];
        for(int i=0;i<size;i++)
            visited[i]=false;
    }


    /** remove min element from array.
     */
    public int removeMin(){
        int v =0;
        //initialize with non visited node.
        for (int i=0;i<dist.length;i++)
            if((!visited[i]))
                    v = i;
        //find min among non visited nodes
        //O(n) runtime.
        for (int i=0;i<dist.length;i++)
            if((!visited[i]) && (dist[i] < dist[v]))
                v=i;
        visited[v]=true;
        return v;
    }

    public void decreaseKey(int vertex, int newValue){
        dist[vertex] = newValue;
    }

    public void insert(int vertex, int weight){
        dist[vertex]=weight;
    }

    public boolean isEmpty(){
        //check if any non inifinity node is not visited yet.
        for (int i=0;i<dist.length;i++)
            if((dist[i] != Graph.INFINITY) && (!visited[i])){
                //System.out.println("G"+i);
                return false;
            }
        return true;

    }

    public boolean contains(int vertex){
        return true;//(dist[vertex] != Graph.INFINITY);
    }


    public static void main(String[] args){
        //ArrayQueue a = new ArrayQueue();

    }

}