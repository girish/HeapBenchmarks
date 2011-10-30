/**
 * Wraps around BinomialHeap to make and indexed queue.
 */

class BinomialQueue implements PQ {

    private int[] dist;
    private Bnode[] d; //index for nodes to make contains O(1)
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

    // delegate to BinomialHeap b.
    public int removeMin(){
        return b.removeMin();
    }

    // delegate to BinomialHeap b.
    public void decreaseKey(int vertex, int newValue){
        dist[vertex] = newValue;
        b.decreaseKey(vertex,newValue,d);
    }

    // delegate to BinomialHeap b.
    public void insert(int vertex, int weight){
        dist[vertex]=weight;
        d[vertex] = new Bnode(vertex, weight);
        b.insert(d[vertex]);
    }

    // delegate to BinomialHeap b.
    public boolean isEmpty(){
        return b.isEmpty();
    }

    //check in our index. 
    public boolean contains(int vertex){
        return (d[vertex] != null);
    }
}