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
    private ListNode head;
    private ListNode last;
    private int size;
    public LinkedList(){
        this.head = null;
        last = head;
    }
    public void add(int vertex, int weight){
        if(last!=null){
           this.head = new ListNode(vertex, weight);
           last  = head;
        }
        else{
            last.next = new ListNode(vertex, weight);
            last = last.next;
        }
    }
}

class Graph {
    public static final double INFINITY = Double.MAX_VALUE;
    private int vertices;
    private int edges;
    private LinkedList[] adj;

    void Graph(int vertices){
        this.vertices = vertices;
        this.edges = 0;
    }

    void intializeArray(){
    }

}

public class ssp {
    public static void main(String[] args){
        System.out.println("WTF");
    }
}