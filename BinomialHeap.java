public class BinomialHeap
{
    /** The head of the singly linked root list. */
    private Bnode head;

    /** Creates an empty binomial heap. */
    public BinomialHeap()
    {
        head = null;        // make an empty root list
    }

    public void insert(Bnode x)
    {
        BinomialHeap hPrime = new BinomialHeap();
        hPrime.head = x;
        BinomialHeap newHeap = (BinomialHeap) this.union(hPrime);
        head = newHeap.head;
    }

    public boolean isEmpty(){
        return head == null;
    }

    /** Removes and returns the smallest object in the binomial
     * heap. */
    public int extractMin()
    {
        // Special case for an empty binomial heap.
        if (head == null)
            return -1;

        // Find the root x with the minimum key in the root list.
        Bnode x = head;      // node with minimum key
        Bnode y = x.sibling; // current node being examined
        Bnode pred = x;      // y's predecessor
        Bnode xPred = null;  // predecessor of x

        while (y != null) {
            if (y.key < x.key) {
                x = y;
                xPred = pred;
            }
            pred = y;
            y = y.sibling;
        }

        removeFromRootList(x, xPred);
        return x.vertex;
    }

    /**
     * Helper method to remove a node from the root list.
     *
     * @param x The node to remove from the root list.
     * @param pred The predecessor of <code>x</code> in the root list,
     * or <code>null</code> if <code>x</code> is the first node in the
     * root list.
     */
    private void removeFromRootList(Bnode x, Bnode pred)
    {
        // Splice out x.
        if (x == head)
            head = x.sibling;
        else
            pred.sibling = x.sibling;

        BinomialHeap hPrime = new BinomialHeap();

        // Reverse the order of x's children, setting hPrime.head to
        // point to the head of the resulting list.
        Bnode z = x.child;
        while (z != null) {
            Bnode next = z.sibling;
            z.sibling = hPrime.head;
            hPrime.head = z;
            z = next;
        }

        BinomialHeap newHeap = (BinomialHeap) this.union(hPrime);
        head = newHeap.head;
    }

    /**
     * Creates a new binomial heap that contains all the elements of
     * two binomial heaps.  One of the original binomial heaps is
     * the object on which this method is called; the other is
     * specified by the parameter.  The two original binomial heaps
     * should no longer be used after this operation.
     *
     * <p>
     *
     * Implements the Binomial-Heap-Union procedure on page 463,
     * with h1 being this object.
     *
     * @param h2 The binomial heap to be merged with this one.
     * @return The new binomial heap that contains all the elements
     * of this binomial heap and <code>h2</code>.
     */
    public BinomialHeap union(BinomialHeap h2)
    {
        BinomialHeap h = new BinomialHeap();
        h.head = binomialHeapMerge(this, (BinomialHeap) h2);
        head = null;                 // no longer using the...
        ((BinomialHeap) h2).head = null; // ...two input lists

        if (h.head == null)
            return h;

        Bnode prevX = null;
        Bnode x = h.head;
        Bnode nextX = x.sibling;

        while (nextX != null) {
            if (x.degree != nextX.degree ||
                    (nextX.sibling != null && nextX.sibling.degree == x.degree)) {
                // Cases 1 and 2.
                prevX = x;
                x = nextX;
                    }
            else {
                if (x.key < nextX.key) {
                    // Case 3.
                    x.sibling = nextX.sibling;
                    binomialLink(nextX, x);
                }
                else {
                    // Case 4.
                    if (prevX == null)
                        h.head = nextX;
                    else
                        prevX.sibling = nextX;

                    binomialLink(x, nextX);
                    x = nextX;
                }
            }

            nextX = x.sibling;
        }

        return h;
    }

    /**
     * Links one binomial tree to another.
     *
     * @param y The root of one binomial tree.
     * @param z The root of another binomial tree; this root becomes
     * the parent of <code>y</code>.
     */
    private void binomialLink(Bnode y, Bnode z)
    {
        y.p = z;
        y.sibling = z.child;
        z.child = y;
        z.degree++;
    }

    /**
     * Merges the root lists of two binomial heaps together into a
     * single root list.  The degrees in the merged root list appear
     * in monotonically increasing order.
     *
     * @param h1 One binomial heap.
     * @param h2 The other binomial heap.
     * @return The head of the merged list.
     */
    private static Bnode binomialHeapMerge(BinomialHeap h1, BinomialHeap h2)
    {
        // If either root list is empty, just return the other.
        if (h1.head == null)
            return h2.head;
        else if (h2.head == null)
            return h1.head;
        else {
            // Neither root list is empty.  Scan through both, always
            // using the node whose degree is smallest of those not
            // yet taken.
            Bnode head;        // head of merged list
            Bnode tail;        // last node added to merged list
            Bnode h1Next = h1.head,
                 h2Next = h2.head; // next nodes to be examined in h1 and h2

            if (h1.head.degree <= h2.head.degree) {
                head = h1.head;
                h1Next = h1Next.sibling;
            }
            else {
                head = h2.head;
                h2Next = h2Next.sibling;
            }

            tail = head;

            // Go through both root lists until one of them is
            // exhausted.
            while (h1Next != null && h2Next != null) {
                if (h1Next.degree <= h2Next.degree) {
                    tail.sibling = h1Next;
                    h1Next = h1Next.sibling;
                }
                else {
                    tail.sibling = h2Next;
                    h2Next = h2Next.sibling;
                }

                tail = tail.sibling;
            }

            // The above loop ended because exactly one of the root
            // lists was exhuasted.  Splice the remainder of whichever
            // root list was not exhausted onto the list we're
            // constructing.
            if (h1Next != null)
                tail.sibling = h1Next;
            else
                tail.sibling = h2Next;

            return head;    // all done!
        }
    }

    /**
     * Decreases the key of a node.  Implements the
     * Binomial-Heap-Decrease-Key procedure on page 470.
     *
     */
    public void decreaseKey(int vertex, int k, Bnode[] dist)
    {
        Bnode x = dist[vertex];
        x.key = k; // update x's key
        Bnode y = x;
        Bnode z = y.p;

        while (z != null && (y.key < z.key )) {
            // Exchange the contents of y and z
            int v = y.key;
            y.key = z.key;
            z.key = v;
            v = y.vertex;
            y.vertex = z.vertex;
            z.vertex = v;

            dist[z.vertex] = z;
            dist[y.vertex] = y;

            y = z;
            z = y.p;
        }
    }


    public static void main(String[] args){
        BinomialHeap b = new BinomialHeap();
        Bnode[] t;
        t = new Bnode[10];
        t[0] = new Bnode(0, 14);
        t[1] = new Bnode(1, 4);
        t[2] = new Bnode(2, 24);
        t[3] = new Bnode(3, 34);
        t[4] = new Bnode(4, 4);
        t[5] = new Bnode(5, 54);
        t[6] = new Bnode(6, 10);
        t[7] = new Bnode(7, 7);
        b.insert(t[0]);
        b.insert(t[1]);
        b.insert(t[2]);
        b.insert(t[3]);
        b.insert(t[4]);
        b.insert(t[5]);
        b.insert(t[6]);
        b.insert(t[7]);
        b.decreaseKey(2,2,t);
        b.decreaseKey(3,3,t);
        b.decreaseKey(4,4,t);
        b.decreaseKey(6,6,t);

        while( !b.isEmpty()){
            int x = b.extractMin();
            System.out.println(t[x].vertex + "--" + t[x].key);
        }
    }
}
