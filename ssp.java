class BinaryHeap {

}

class FibonacciHeap{

}

class ListNode {
    int vertex;
    int weight;
    public ListNode next;
    public ListNode(int vertex,int weight){
        this.vertex = vertex;
        this.weight = weight;
        this.next = null;
    }
}

class LinkedList {
    public ListNode head;
    private ListNode last;
    private int size;
    public LinkedList(){
        this.head = null;
        last = head;
    }
    public void add(int vertex, int weight){
        if(last==null){
           this.head = new ListNode(vertex, weight);
           last  = head;
        }
        else{
            last.next = new ListNode(vertex, weight);
            last = last.next;
        }
    }
    public void print(){
        ListNode l = head;
        while(l!=null){
            System.out.println(l.weight);
            l = l.next;
        }

    }
}

class Graph {
    public static final double INFINITY = Double.MAX_VALUE;
    private int vertices;
    private LinkedList[] adj;

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
        for(int i=0; i<vertices;i++)
            for(int j=0; j<vertices;j++ ){
                int weight = (int) Math.round(100 * Math.random());
                System.out.println(weight);
                adj[i].add(j, weight);
            }
    }

    public void print(){
        for(int i=0; i<vertices;i++ )
            //System.out.println(adj[i].print());
            adj[i].print();
    }

    private void addEdge(int vertex, int weight){
    }

}

class Dsp {
    private int[] dist;
    private int[] edgeto;
    private int[] queue;

    public Dsp(Graph G, int source){

    }
}

public class ssp {
    public static void main(String[] args){
        Graph G=new Graph(2);
        G.RandomGraph();
        G.print();
    }
}