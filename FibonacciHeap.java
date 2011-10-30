public class FibonacciHeap
{
    private Fnode min;

    private int nCount;//total node count

    //Initialize empty heap
    public FibonacciHeap()
    {
        min = null;
        nCount =0;
    }

    public boolean isEmpty()
    {
        return min == null;
    }

    /**
     * Decrease key of a node. 
     */
    public void decreaseKey(Fnode d, int k)
    {
        d.key = k;

        Fnode p = d.parent;

        //When it voilates heap property other do nothing.
        if ((p != null) && (d.key < p.key)) {
            cut(d, p); // cut d and put it in root list
            cascadingCut(p);// cut all nodes that are marked false stop at the first unmarked one encountered and mark true.
        }

        if (d.key < min.key) {
            min = d;
        }
    }

    /** Just insert in doubly linked list at root
     * change root if inserted key is less than current 
     * root
     *
     * node: node to insert
     */
    public void insert(Fnode node)
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

        nCount++;
    }

    /** Remove min key from Heap. 
     *
     */
    public int removeMin()
    {
        Fnode z = min;

        if (z != null) {
            int numKids = z.degree;
            Fnode x = z.child;
            Fnode tempRight;

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
                consolidate(); // pair heaps if neccassary
            }

            // decrement size of heap
            nCount--;
        }

        return z.data;
    }


    /**
     * Travel above, cut all nodes that are marked true and add them to root list
     * stop at first unmarked node and mark it true.
     */
    protected void cascadingCut(Fnode y)
    {
        Fnode z = y.parent;

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

    /**
     * Combine/Pair nodes with same degrees after remove min.
     * algo described in cormen.
     */

    protected void consolidate()
    {
        // limit for possible degrees based on Fibonacci gloden 
        // ratio.
        final double phi = (1.0 + Math.sqrt(5.0)) / 2.0;

        int arraySize = (int) Math.floor(Math.log(nCount) / Math.log(phi));

        Fnode[] array=new Fnode[arraySize+1]; // degree index

        for (int i = 0; i < arraySize; i++) {
            array[i] = null;
        }
        int numRoots = 0; //nodes in root list.
        Fnode x = min;

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
            Fnode next = x.right;

            // go until u find node of same degree same degree.
            while (array[d]!=null) {
                Fnode y = array[d];
                // There is, make one of the nodes a child of the other.
                // Do this based on the key value.
                if (x.key > y.key) {
                    Fnode temp = y;
                    y = x;
                    x = temp;
                }

                pair(y, x); //pair nodes with same degree

                // set degree to null , go to next one.
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

        // Reintialize tree from array
        min = null;

        for (int i = 0; i < arraySize; i++) {
            Fnode y = array[i];
            if (y == null) {
                continue;
            }

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
     * x child of y to be removed from y's child list
     * y parent of x about to lose a child
     */
    protected void cut(Fnode x, Fnode y)
    {
        // remove x from childlist of y and decrease degree[y]
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

    /**
     *  Pair/combine two node in root list during consolidate
     *  operation.
     * less node to become child
     * great x node to become parent
     */
    protected void pair(Fnode less, Fnode great)
    {
        // remove less from root list of heap
        less.left.right = less.right;
        less.right.left = less.left;

        // make less a child of great
        less.parent = great;

        if (great.child == null) {
            great.child = less;
            less.right = less;
            less.left = less;
        } else {
            less.left = great.child;
            less.right = great.child.right;
            great.child.right = less;
            less.right.left = less;
        }

        // increase degree[great] and mark as false as it is in
        // root list.
        great.degree++;
        less.mark = false;
    }

    public static void main(String[] args){
        FibonacciHeap b = new FibonacciHeap();
        Fnode[] t;
        t = new Fnode[10000];
        for(int i=0;i<10000;i++){
            t[i] = new Fnode(i,(int)Math.round(1000*Math.random()));
            b.insert(t[i]);
        }
        //t[0] = new Fnode(0, 0);
        //t[1] = new Fnode(1, 1);
        //t[2] = new Fnode(2, 2);
        //t[3] = new Fnode(3, 3);
        //t[4] = new Fnode(4, 4);
        //t[5] = new Fnode(5, 5);
        //t[6] = new Fnode(6, 6);
        //t[7] = new Fnode(7, 7);
        //b.insert(t[0], 0);
        //b.insert(t[1], 1);
        //b.insert(t[2], 12);
        //b.insert(t[3], 13);
        //b.insert(t[4], 14);
        //b.insert(t[5], 15);
        //b.insert(t[6], 16);
        //b.insert(t[7], 17);
        b.decreaseKey(t[3],3);
        ////b.decreaseKey(3,3);
        b.decreaseKey(t[4],4);
        ///b.decreaseKey(6,6);

        while( !b.isEmpty()){
            int x = b.removeMin();
            System.out.println(t[x].data + "--" + x +"---" + t[x].key);
        }
    }

}

