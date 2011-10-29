class BinomialQueue implements PQ {

    private int[] dist;
    private Bnode[] d;
    private BinomialHeap b;
    private int size;


    public BinomialQueue(int[] dist){
        this.size  = dist.length;
        //this.dist = new int[size];
        this.dist = dist;
        this.d = new Bnode[size];
        this.b = new BinomialHeap();
        for(int i=0; i< size;i++)
            d[i]=null;
    }

    public int delMin(){
        return b.extractMin();
    }

    public void decreaseKey(int vertex, int newValue){
        dist[vertex] = newValue;
        b.decreaseKey(vertex,newValue,d);
    }

    public void insert(int vertex, int weight){
        dist[vertex]=weight;
        d[vertex] = new Bnode(vertex, weight);
        b.insert(d[vertex]);
    }

    public boolean isEmpty(){
        return b.isEmpty();
    }

    public boolean contains(int vertex){
        return (d[vertex] != null);
    }
}