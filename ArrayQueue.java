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
        int v=0;
        for (int i=0;i<size;i++)
            if((visited[i] == false) && (dist[i] < dist[v]))
                v=i;
        visited[v]=true;
        size--;
        return v;
    }

    public void decreaseKey(int vertex, int newValue){
        dist[vertex] = newValue;
    }

    public void insert(int vertex, int weight){
        dist[vertex]=weight;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public boolean contains(int vertex){
        return (dist[vertex] != Graph.INFINITY);
    }
}