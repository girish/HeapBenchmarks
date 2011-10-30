class FibonacciQueue implements PQ {

    private int[] dist;
    private Fnode[] d;
    private FibonacciHeap f;
    private int size;


    public FibonacciQueue(int[] dist){
        this.size  = dist.length;
        //this.dist = new int[size];
        this.dist = dist;
        this.d = new Fnode[size];
        this.f = new FibonacciHeap();
        for(int i=0; i< size;i++)
            d[i]=null;
    }

    public int removeMin(){
        return f.removeMin();
    }

    public void decreaseKey(int vertex, int newValue){
        dist[vertex] = newValue;
        f.decreaseKey(d[vertex],newValue);
    }

    public void insert(int vertex, int weight){
        dist[vertex]=weight;
        d[vertex] = new Fnode(vertex, weight);
        f.insert(d[vertex]);
    }

    public boolean isEmpty(){
        return f.isEmpty();
    }

    public boolean contains(int vertex){
        return (d[vertex] != null);
    }
}