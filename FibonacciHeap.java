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
        Fnode tempH = min;

        if (tempH != null) {
            Fnode current = tempH.child;
            Fnode tempRight;
            // for each child of tempH do...
            for(int i=tempH.degree;i>0;i--) {
                tempRight = current.right;

                // remove current from child list
                current.left.right = current.right;
                current.right.left = current.left;

                // add current to root list of heap
                current.left = min;
                current.right = min.right;
                min.right = current;
                current.right.left = current;

                // set parent[current] to null
                current.parent = null;
                current = tempRight;
            }

            // remove tempH from root list of heap
            tempH.left.right = tempH.right;
            tempH.right.left = tempH.left;

            if (tempH == tempH.right) {
                min = null;
            } else {
                min = tempH.right;
                consolidate(); // pair heaps if neccassary
            }

            // decrement size of heap
            nCount--;
        }

        return tempH.data;
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
        if(min == null)
            return;

        // limit for possible degrees based on Fibonacci gloden
        // ratio.
        final double phi = (1.0 + Math.sqrt(5.0)) / 2.0;

        int dCount = (int) Math.floor(Math.log(nCount) / Math.log(phi));

        Fnode[] dIndex=new Fnode[dCount+1]; // degree index

        for (int i = 0; i < dCount; i++) {
            dIndex[i] = null;
        }
        int rootCount = 0; //nodes in root list.
        Fnode x = min;

        if (x != null) {
            rootCount++;
            x = x.right;

            while (x != min) {
                rootCount++;
                x = x.right;
            }
        }

        // For each node in root list do...
        while (rootCount > 0) {
            // Access this node's degree..
            int d = x.degree;
            Fnode next = x.right;

            // go until u find node of same degree same degree.
            while (dIndex[d]!=null) {
                Fnode y = dIndex[d];

                if (x.key > y.key) {
                    Fnode temp = y;
                    y = x;
                    x = temp;
                }

                pair(y, x); //pair nodes with same degree

                // set degree to null , go to next one.
                dIndex[d]= null;
                d++;
            }

            // Save this node for later when we might encounter another
            // of the same degree.
            dIndex[d]= x;

            x = next;
            rootCount--;
        }

        // Reintialize tree from dIndex
        min = null;

        for (int i = 0; i < dCount; i++) {
            Fnode y = dIndex[i];
            if (y!= null) {
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
    }

    /**
     * Cut the node and put it in root list.
     * node: to be removed
     * paren: parent node to node
     */
    protected void cut(Fnode node, Fnode paren)
    {
        // remove x from childlist of y and decrease degree[y]
        node.left.right = node.right;
        node.right.left = node.left;
        paren.degree--;

        // reset paren.child if necessary
        if (paren.child == node) {
            paren.child = node.right;
        }

        if (paren.degree == 0) {
            paren.child = null;
        }

        // add node to root list of heap
        node.left = min;
        node.right = min.right;
        min.right = node;
        node.right.left = node;

        // set parent[node] to nil
        node.parent = null;

        // set mark[node] to false
        node.mark = false;
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

