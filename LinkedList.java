import java.util.Iterator;
import java.util.NoSuchElementException;

// Node with vertex and distance wieght.
class ListNode {
    int toVertex;
    int weight;
    public ListNode next;
    public ListNode(int vertex,int weight){
        this.toVertex = vertex;
        this.weight = weight;
        this.next = null;
    }
    public int to(){
        return toVertex;
    }
    public int weight(){
        return weight;
    }
}
/**
 * LinkedList class used in Graph.
 */
class LinkedList implements Iterable<ListNode>{
    public ListNode head;
    private ListNode last;
    private int size;
    public LinkedList(){
        this.head = null;
        last = head; // easy insertion
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

    public String toString(){
        ListNode l = head;
        String s = "";
        while(l!=null){
            s = s + "--" + l.toVertex+"  "+l.weight;
            l = l.next;
        }
        return s;
    }


    public Iterator<ListNode> iterator()  {
        return new ListIterator();
    }

    // for easy iteration
    private class ListIterator implements Iterator<ListNode> {
        private ListNode current = head;

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public ListNode next() {
            if (!hasNext()) throw new NoSuchElementException();
            ListNode item = current;
            current = current.next;
            return item;
        }
    }
}
