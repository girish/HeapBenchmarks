class Node
{
    /** The object stored in this node. */
    public int key;

    public int vertex;

    /** This node's parent, or <code>null</code> if this node is a
     * root. */
    public Node p;

    /** Some child of this node. */
    public Node child;

    /** This node's right sibling. */
    public Node right;

    /** This node's left sibling. */
    public Node left;

    /** How many children this node has. */
    public int degree;

    /** <code>true</code> if this node is marked,
     * <code>false</code> if not marked. */
    public boolean mark;

    /**
     * Creates a new node.
     *
     * @param e The dynamic set element to store in the node.
     */
    public Node(int vertex, int distance)
    {
        p = null;
        this.vertex = vertex;
        this.key = distance;
        child = null;
        right = this;
        left = this;
        degree = 0;
        mark = false;
    }

}
public class FibHeap
{
    /** The node in root list with the minimum key. */
    private Node min;

    /** How many nodes are in this Fibonacci heap. */
    int n;

    /** Creates an empty Fibonacci heap. */
    public FibHeap()
    {
        min = null;     // make an empty root list
        n = 0;          // no nodes yet
    }

    /** Inner class for a node within a Fibonaaci heap. */

    /**

    /**
     * Inserts a dynamic set element into the Fibonacci heap.
     * Implements the Fib-Heap-Insert procedure on page 480.
     *
     * @param e The dynamic set element to be inserted.
     * @return A handle to the inserted item.
     */
    public void insert(Node x)
    {

        // Splice this new node into the root list.  The return value
        // will be the value of min before the call, if min was
        // already non-null, or x, if min was null.
        min = spliceIn(min, x);

        // Update min if necessary.
        if (x.key < min.key)
            min = x;

        n++;
    }

    /**
     * Splices the node list containing one node into the node list
     * containing another node, just to the left of it.
     *
     * @param x The node whose list is to be spliced into the other.
     * @param y The node whose list is to be spliced into.
     * @return If <code>x</code> is <code>null</code>, then
     * <code>x</code>'s list is empty, and we just return
     * <code>y</code>.  If <code>y</code> is <code>null</code>, then
     * <code>y</code>'s list is empty and we just return
     * <code>x</code>.  If neither <code>x</code> nor <code>y</code>
     * is <code>null</code>, we return <code>x</code> as a pointer
     * into the list.
     */
    private Node spliceIn(Node x, Node y)
    {
        if (x == null)
            return y;
        else if (y == null)
            return x;
        else {
            //Node xPred = x.left;
            //Node yTail = y.left;

            //x.left = yTail;
            //yTail.right = x;
            //y.left = xPred;
            //xPred.right = y;
            y.left = x;
            y.right = x.right;
            min.right = y;
            min.right.left = y;
            return x;
        }
    }   

    /** Returns the object whose key is minimum. */
    public int minimum()
    {
        if (min == null)
            return -1;
        else
            return min.vertex;
    }

    /** Removes and returns the smallest object in the Fibonacci heap.
     * Implements the Fib-Heap-Extract-Min procedure on
     * page 483. */
    public int extractMin()
    {

        /*
           Node z = min;

           if (z == null)
           return -1;    // empty Fibonacci heap
           else {
        // Add each child of z to the root list.  We can just
        // splice in z's children, but first we have to set their
        // parent pointers to null.
        //
        int num_child = min.degree;
        while(num_child > 0){
        if (z.child != null) {
        z.child.p = null;

        for (Node x = z.child.right; x != z.child; x = x.right)
        x.p = null;

        min = spliceIn(min, z.child);
        }

        // Remove z from the root list.
        z.left.right = z.right;
        z.right.left = z.left;

        // Update min appropriately.
        if (z == z.right)
        min = null;
        else {
        min = z.right;
        consolidate();
        }

        n--;

        // Clear out z's pointers.
        z.p = null;
        z.left = null;
        z.right = null;

        return z.vertex;

*/
        Node z = min;

        if (z != null) {
            int numKids = z.degree;
            Node x = z.child;
            Node tempRight;

            // for each child of z do...
            while (numKids > 0) {
                tempRight = x.right;

                // remove x from child list
                x.left.right = x.right;
                x.right.left = x.left;

                // add x to root list of heap
                x.left = min;
                x.right = min.right;
                min.right = x;
                x.right.left = x;

                // set parent[x] to null
                x.p = null;
                x = tempRight;
                numKids--;
            }

            // remove z from root list of heap
            z.left.right = z.right;
            z.right.left = z.left;

            if (z == z.right) {
                min = null;
            } else {
                min = z.right;
                consolidate();
            }

            // decrement size of heap
            n--;

        }

        return z.vertex;
    }

    /** Consolidates the root list of this Fibonacci heap.  Implements
     * the Consolidate procedure on page 486. */
    private void consolidate()
    {
        Node[] a = new Node[computeD() + 1];
        for (int i = 0; i < a.length; i++)
            a[i] = null;

        // We can use a do-while loop because we know that consolidate
        // is called only when the root list is not empty.
        Node w = min;
        Node start = min;   // stop when w gets back to start
        Node x = min;
        int numRoots=0;

        if (x != null) {
            numRoots++;
            x = x.right;

            while (x != min) {
                numRoots++;
                x = x.right;
            }
        }
        while (numRoots > 0){
            Node nextW = x.right; // because we might move w, save its
            // right sibling
            int d = x.degree;

            while (a[d] != null) {
                Node y = a[d];
                if (x.key < y.key) {
                    // Exchange x and y.
                    Node temp = x;
                    x = y;
                    y = temp;
                }

                // If we're removing the starting point of the root
                // list, make the starting point be its right sibling.
                //if (y == start)
                //    start = start.right;

                fibHeapLink(y, x);
                a[d] = null;
                d++;
            }

            a[d] = x;
            x = nextW;
            numRoots--;
        }

        min = null;

        for (int i = 0; i < a.length; i++) {
            if (a[i] != null) {
                // Add a[i] to the root list.
                a[i].right = a[i];
                a[i].left = a[i];
                min = spliceIn(min, a[i]);
                if (a[i].key < min.key)
                    min = a[i];
            }
        }
    }

    /**
     * Links one root to another.  Implements the Fib-Heap-Link
     * procedure on page 486.
     *
     * @param y The root to be linked to <code>x</code>.
     * @param x The root that <code>y</code> is linked to.
     */
    private void fibHeapLink(Node y, Node x)
    {
        // Remove y from its root list.
        y.left.right = y.right;


        // Make y a child of x, incrementing degree x.
        y.left = y;
        y.right = y;
        x.child = spliceIn(x.child, y);
        x.degree++;

        y.mark = false;
    }

    /** Returns D(n) = floor(log base phi of n), where phi = (1 +
     * sqrt(5)) / 2.  Assumes that n > 0. */
    private int computeD()
    {
        final double phi = (1.0 + Math.sqrt(5.0)) / 2.0;

        // log base phi of n = (ln n) / (ln phi).
        return (int) Math.floor(Math.log(n) / Math.log(phi));
    }

    /**
    /**
     * Decreases the key of a node.  Implements the
     * Fib-Heap-Decrease-Key procedure on page 489.
     *
     * @param node The node whose key is to be decreased.
     * @param k The new key.
     * @throws KeyUpdateException if the new key is greater than the
     * current key.
     */
    public void decreaseKey(Object node, int k)
    {
        Node x = (Node) node;

        // Finish the procedure.
        x.key = k; // update x's key
        updateForDecreaseKey(x, false);
    }

    /**
     * Changes the structure of the Fibonacci heap as part of a
     * <code>decreaseKey</code> operation.
     *
     * @param x The node whose key is being decreased.
     * @param delete If <code>true</code>, ignore <code>x</code>'s key
     * and treat it as though it were negative infinity, because we're
     * decreasing the key as part of a <code>delete</code> operation.
     */
    private void updateForDecreaseKey(Node x, boolean delete)
    {
        Node y = x.p;

        if (y != null && (delete || x.key < y.key)) {
            cut(x, y);
            cascadingCut(y);
        }

        if (delete || x.key < min.key)
            min = x;
    }

    /**
     * Cuts the link between a node and its parent.  Implements the
     * Cut procedure on page 489.
     *
     * @param x The node.
     * @param y <code>x</code>'s parent.
     */
    private void cut(Node x, Node y)
    {
        // Remove x from y's child list, decrementing y's degree.
        Node xRight = x.right;
        x.left.right = x.right;
        x.right.left = x.left;
        y.degree--;
        if (y.degree == 0)
            y.child = null;
        else if (y.child == x)
            y.child = xRight;

        // Add x to the root list.
        x.right = x;
        x.left = x;
        min = spliceIn(min, x);

        x.p = null;
        x.mark = false;
    }

    /**
     * Performs a cascading cut operation.  Implements the
     * Cascading-Cut procedure on page 490.
     *
     * @param y The node being cut.
     */
    private void cascadingCut(Node y)
    {
        Node z = y.p;

        if (z != null) {
            if (!y.mark)
                y.mark = true;
            else {
                cut(y, z);
                cascadingCut(z);
            }
        }
    }

    /**
     * Deletes a node.  Implements the Fib-Heap-Delete procedure on
     * page 492.
     *
     * @param node The node to be deleted.
     */
    public void delete(Object node)
    {
        Node x = (Node) node;
        updateForDecreaseKey(x, true);
        extractMin();
    }

    /**

    /**
     * Marks a node.  We use this method just so we can create
     * examples exactly like those in the text.
     *
     * @param node The node to be marked.
     */
    public void mark(Object node)
    {
        ((Node) node).mark = true;
    }

    public boolean isEmpty(){
        return min == null;

    }
    public static void main(String[] args){
        FibHeap b = new FibHeap();
        Node[] t;
        t = new Node[10];
        t[0] = new Node(0, 14);
        t[1] = new Node(1, 4);
        t[2] = new Node(2, 24);
        t[3] = new Node(3, 34);
        t[4] = new Node(4, 5);
        t[5] = new Node(5, 54);
        t[6] = new Node(6, 10);
        t[7] = new Node(7, 7);
        b.insert(t[0]);
        b.insert(t[1]);
        b.insert(t[2]);
        b.insert(t[3]);
        b.insert(t[4]);
        b.insert(t[5]);
        b.insert(t[6]);
        b.insert(t[7]);
        //b.decreaseKey(t[2],2);
        //b.decreaseKey(t[3],3);
        //b.decreaseKey(t[4],3);
        //b.decreaseKey(t[6],6);

        while( !b.isEmpty()){
            int x = b.extractMin();
            System.out.println(x+ "---"+t[x].vertex + "--" + t[x].key);
        }
    }
}

// $Id: FibHeap.java,v 1.1 2003/10/14 16:56:20 thc Exp $
// $Log: FibHeap.java,v $
// Revision 1.1  2003/10/14 16:56:20  thc
// Initial revision.
//