/**
 * Wraps around FibonacciHeap to make an indexed heap.
 */

class FibonacciQueue implements PQ {

    private int[] dist;
    private Fnode[] d; //index for nodes to make O(1) contains
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

    //delegate it to FibonacciHeap f
    public int removeMin(){
        return f.removeMin();
    }

    //delegate it to FibonacciHeap f
    public void decreaseKey(int vertex, int newValue){
        dist[vertex] = newValue;
        f.decreaseKey(d[vertex],newValue);
    }

    //delegate it to FibonacciHeap f
    public void insert(int vertex, int weight){
        dist[vertex]=weight;
        d[vertex] = new Fnode(vertex, weight);
        f.insert(d[vertex]);
    }

    //delegate it to FibonacciHeap f
    public boolean isEmpty(){
        return f.isEmpty();
    }

    //make use of our index.
    public boolean contains(int vertex){
        return (d[vertex] != null);
    }
}