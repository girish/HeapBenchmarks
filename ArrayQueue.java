class ArrayQueue implements PQ{
    private int[] dist;
    private boolean[] visited;
    private int size;

    public ArrayQueue(int[] dist){
        this.size  = dist.length;
        //this.dist = new int[size];
        this.dist = dist;
        this.visited = new boolean[size];
        for(int i=0;i<size;i++)
            visited[i]=false;
    }

    public int removeMin(){
        int v =0;
        for (int i=0;i<dist.length;i++)
            if((!visited[i]))
                    v = i;
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
        for (int i=0;i<dist.length;i++)
            if((dist[i] != Graph.INFINITY) && (!visited[i])){
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