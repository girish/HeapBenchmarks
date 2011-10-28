
import java.util.*;


public class FibonacciHeap
{


    private FibonacciHeapNode min;

    private int nNodes;

    public FibonacciHeap()
    {
        min = null;
        nNodes =0;
    }

    public boolean isEmpty()
    {
        return min == null;
    }

    public void decreaseKey(FibonacciHeapNode x, int k)
    {
        if (k > x.key) {
            throw new IllegalArgumentException(
                "decreaseKey() got larger key value");
        }

        x.key = k;

        FibonacciHeapNode y = x.parent;

        if ((y != null) && (x.key < y.key)) {
            cut(x, y);
            cascadingCut(y);
        }

        if (x.key < min.key) {
            min = x;
        }
    }

    public void insert(FibonacciHeapNode node)
    {
        if (min != null) {
            node.left = min;
            node.right = min.right;
            min.right = node;
            node.right.left = node;

            if (node.key < min.key) {
                min = node;
            }
        } else {
            min = node;
        }

        nNodes++;
    }


    public int removeMin()
    {
        FibonacciHeapNode z = min;

        if (z != null) {
            int numKids = z.degree;
            FibonacciHeapNode x = z.child;
            FibonacciHeapNode tempRight;

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
                x.parent = null;
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
            nNodes--;
        }

        return z.data;
    }

    public int size()
    {
        return nNodes;
    }

    protected void cascadingCut(FibonacciHeapNode y)
    {
        FibonacciHeapNode z = y.parent;

        // if there's a parent...
        if (z != null) {
            // if y is unmarked, set it marked
            if (!y.mark) {
                y.mark = true;
            } else {
                // it's marked, cut it from parent
                cut(y, z);

                // cut its parent as well
                cascadingCut(z);
            }
        }
    }

    // cascadingCut

    protected void consolidate()
    {
        final double phi = (1.0 + Math.sqrt(5.0)) / 2.0;

        int arraySize = (int) Math.floor(Math.log(nNodes) / Math.log(phi));

        FibonacciHeapNode[] array=new FibonacciHeapNode[arraySize];

        for (int i = 0; i < arraySize; i++) {
            array[i] = null;
        }

        int numRoots = 0;
        FibonacciHeapNode x = min;

        if (x != null) {
            numRoots++;
            x = x.right;

            while (x != min) {
                numRoots++;
                x = x.right;
            }
        }

        // For each node in root list do...
        while (numRoots > 0) {
            // Access this node's degree..
            int d = x.degree;
            FibonacciHeapNode next = x.right;

            // ..and see if there's another of the same degree.
            while (array[d]!=null) {
                FibonacciHeapNode y = array[d];
                // There is, make one of the nodes a child of the other.
                // Do this based on the key value.
                if (x.key > y.key) {
                    FibonacciHeapNode temp = y;
                    y = x;
                    x = temp;
                }

                // FibonacciHeapNode<T> y disappears from root list.
                link(y, x);

                // We've handled this degree, go to next one.
                array[d]= null;
                d++;
            }

            // Save this node for later when we might encounter another
            // of the same degree.
            array[d]= x;

            // Move forward through list.
            x = next;
            numRoots--;
        }

        // Set min to null (effectively losing the root list) and
        // reconstruct the root list from the array entries in array[].
        min = null;

        for (int i = 0; i < arraySize; i++) {
            FibonacciHeapNode y = array[i];
            if (y == null) {
                continue;
            }

            // We've got a live one, add it to root list.
            if (min != null) {
                // First remove node from root list.
                y.left.right = y.right;
                y.right.left = y.left;

                // Now add to root list, again.
                y.left = min;
                y.right = min.right;
                min.right = y;
                y.right.left = y;

                // Check if this is a new min.
                if (y.key < min.key) {
                    min = y;
                }
            } else {
                min = y;
            }
        }
    }

    /**
     * The reverse of the link operation: removes x from the child list of y.
     * This method assumes that min is non-null.
     *
     * <p>Running time: O(1)</p>
     *
     * @param x child of y to be removed from y's child list
     * @param y parent of x about to lose a child
     */
    protected void cut(FibonacciHeapNode x, FibonacciHeapNode y)
    {
        // remove x from childlist of y and decrement degree[y]
        x.left.right = x.right;
        x.right.left = x.left;
        y.degree--;

        // reset y.child if necessary
        if (y.child == x) {
            y.child = x.right;
        }

        if (y.degree == 0) {
            y.child = null;
        }

        // add x to root list of heap
        x.left = min;
        x.right = min.right;
        min.right = x;
        x.right.left = x;

        // set parent[x] to nil
        x.parent = null;

        // set mark[x] to false
        x.mark = false;
    }

    // cut

    /**
     * Make node y a child of node x.
     *
     * <p>Running time: O(1) actual</p>
     *
     * @param y node to become child
     * @param x node to become parent
     */
    protected void link(FibonacciHeapNode y, FibonacciHeapNode x)
    {
        // remove y from root list of heap
        y.left.right = y.right;
        y.right.left = y.left;

        // make y a child of x
        y.parent = x;

        if (x.child == null) {
            x.child = y;
            y.right = y;
            y.left = y;
        } else {
            y.left = x.child;
            y.right = x.child.right;
            x.child.right = y;
            y.right.left = y;
        }

        // increase degree[x]
        x.degree++;

        // set mark[y] false
        y.mark = false;
    }

    // link
    //
    //
    public static void main(String[] args){
        FibonacciHeap b = new FibonacciHeap();
        FibonacciHeapNode[] t;
        t = new FibonacciHeapNode[10000];
        for(int i=0;i<10000;i++){
            t[i] = new FibonacciHeapNode(i,(int)Math.round(1000*Math.random()));
            b.insert(t[i]);
        }
        //t[0] = new FibonacciHeapNode(0, 0);
        //t[1] = new FibonacciHeapNode(1, 1);
        //t[2] = new FibonacciHeapNode(2, 2);
        //t[3] = new FibonacciHeapNode(3, 3);
        //t[4] = new FibonacciHeapNode(4, 4);
        //t[5] = new FibonacciHeapNode(5, 5);
        //t[6] = new FibonacciHeapNode(6, 6);
        //t[7] = new FibonacciHeapNode(7, 7);
        //b.insert(t[0], 0);
        //b.insert(t[1], 1);
        //b.insert(t[2], 12);
        //b.insert(t[3], 13);
        //b.insert(t[4], 14);
        //b.insert(t[5], 15);
        //b.insert(t[6], 16);
        //b.insert(t[7], 17);
        //b.decreaseKey(t[3],3);
        ////b.decreaseKey(3,3);
        //b.decreaseKey(t[4],4);
        ////b.decreaseKey(6,6);

        while( !b.isEmpty()){
            int x = b.removeMin();
            System.out.println(t[x].data + "--" + x +"---" + t[x].key);
        }
    }
    
}

class FibonacciHeapNode
{
    int data;

    FibonacciHeapNode child;

    /**
     * left sibling node
     */
    FibonacciHeapNode left;

    /**
     * parent node
     */
    FibonacciHeapNode parent;

    /**
     * right sibling node
     */
    FibonacciHeapNode right;

    /**
     * true if this node has had a child removed since this node was added to
     * its parent
     */
    boolean mark;

    /**
     * key value for this node
     */
    int key;

    /**
     * number of children of this node (does not count grandchildren)
     */
    int degree;

    public FibonacciHeapNode(int data, int key)
    {
        right = this;
        left = this;
        this.data = data;
        this.key = key;
    }

}

