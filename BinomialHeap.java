public class BinomialHeap
{
    private Bnode head; //head of root list.

    public BinomialHeap()
    {
        head = null;        // make an empty root list
    }

    /**
     * Inserts element in to the Heap by making heap with one element and merging it.
    */
    public void insert(Bnode x)
    {
        BinomialHeap h = new BinomialHeap();
        h.head = x;
        // create a heap with single node and merge it with current tree.
        BinomialHeap newH = this.meld(h);
        head = newH.head;
    }

    public boolean isEmpty(){
        return head == null;
    }

    /**
     * pair binomial trees.
     * y The root of one binomial tree which will be parent.
     * z The root of another binomial tree which will be child;
     */
    private void pair(Bnode y, Bnode z)
    {
        y.p = z;
        y.sibling = z.child;
        z.child = y;
        z.degree++;
    }

    /**
     *  Merge this heap with another Binomail Heap.
     *  h2 heap to be merged.
    */
    public BinomialHeap meld(BinomialHeap h2)
    {
        BinomialHeap h = new BinomialHeap();
        h.head = mergeRootList(this, h2); //merge root lists for both heaps sorted on degree of tree nodes.
        head = null;
        h2.head = null;

        if (h.head == null)
            return h;

        //Preparing to compare two consective nodes
        Bnode prevX = null;
        Bnode x = h.head;
        Bnode nextX = x.sibling;

        //Merge ones with same degrees. algo from cormen.

        while (nextX != null) {
            //when degree are not equal or this is not last two node when equal move
            //forward.
            if (x.degree != nextX.degree || (nextX.sibling != null && nextX.sibling.degree == x.degree)) {
                prevX = x;
                x = nextX;
            }
            else {
                //if degrees are equal prepare for  compare keys.
                if (x.key < nextX.key) { //when current is less than next
                    x.sibling = nextX.sibling;
                    pair(nextX, x);//combine trees with same degree
                }
                else {
                    // when current is > next.
                    if (prevX == null)
                        h.head = nextX;
                    else
                        prevX.sibling = nextX;

                    pair(x, nextX); //combine trees with same degree
                    x = nextX;
                }
            }

            nextX = x.sibling;
        }

        return h;
    }



    /**
     *Make a root list with head of trees from BinomialHeaps h1 and h2
     *in sorted order of degree.
     */

    private static Bnode mergeRootList(BinomialHeap h1, BinomialHeap h2)
    {
        // If either root list is empty, just return the other.
        if (h1.head == null)
            return h2.head;
        else if (h2.head == null)
            return h1.head;
        else {
            Bnode head;        // head of merged list
            Bnode tail;        // last node added to merged list
            Bnode h1Next = h1.head,
                  h2Next = h2.head; // next nodes to be examined in h1 and h2

            //Initialize for comparision
            //head contains smallest one
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
            // exhausted. merge them in to a sorted list based on degree

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

            //Merge remaining trees from other list that didn't
            //run out first.
            if (h1Next != null)
                tail.sibling = h1Next;
            else
                tail.sibling = h2Next;

            return head;
        }
    }

    /** Removes and returns the smallest object in the binomial
     * heap. */
    public int removeMin()
    {
        // for an empty binomial heap.
        if (head == null)
            return -1;

        Bnode x = head;
        Bnode y = x.sibling; // current node
        Bnode pred = x;      // y prev
        Bnode xPred = null;  // x prev

        // Determine the node x with the minimum key in the root list.
        while (y != null) {
            if (y.key < x.key) {
                x = y;
                xPred = pred;
            }
            pred = y;
            y = y.sibling;
        }
        //remove node x from root list.
        if (x == head)
            head = x.sibling;
        else
            xPred.sibling = x.sibling;

        //Add children to new Bheap and meld with existing tree.
        BinomialHeap h = new BinomialHeap();

        Bnode z = x.child;
        while (z != null) {
            Bnode next = z.sibling;
            z.sibling = h.head;
            h.head = z;
            z = next;
        }
        BinomialHeap newH = this.meld(h);
        head = newH.head;
        return x.vertex;
    }

    /**
     * Decreases the key of a node. Last arguement we pass the index of nodes
     * to make search/contains operation in dijkstra O(1)
     */
    public void decreaseKey(int vertex, int k, Bnode[] dist)
    {
        Bnode x = dist[vertex];//get node from dist
        x.key = k; // update key
        Bnode y = x;
        Bnode z = y.p;

        //move node up if it voilates heap property
        while (z != null && (y.key < z.key )) {
            // Exchange the contents of y and z
            // mangles references to dist.
            int v = y.key;
            y.key = z.key;
            z.key = v;
            v = y.vertex;
            y.vertex = z.vertex;
            z.vertex = v;

            //Swap the nodes in the array of dist which holds references as we are just
            //exchanging contents.
            //Useful for contains operation required in dijkstra.
            dist[z.vertex] = z;
            dist[y.vertex] = y;

            y = z;
            z = y.p;
        }
    }


    public static void main(String[] args){
        BinomialHeap b = new BinomialHeap();
        Bnode[] t;
        //t = new Bnode[10];
        t = new Bnode[10000];
        for(int i=0;i<10000;i++){
            t[i] = new Bnode(i,(int)Math.round(1000*Math.random()));
            b.insert(t[i]);
        }
        //t[0] = new Bnode(0, 14);
        //t[1] = new Bnode(1, 4);
        //t[2] = new Bnode(2, 24);
        //t[3] = new Bnode(3, 34);
        //t[4] = new Bnode(4, 4);
        //t[5] = new Bnode(5, 54);
        //t[6] = new Bnode(6, 10);
        //t[7] = new Bnode(7, 7);
        //b.insert(t[0]);
        //b.insert(t[1]);
        //b.insert(t[2]);
        //b.insert(t[3]);
        //b.insert(t[4]);
        //b.insert(t[5]);
        //b.insert(t[6]);
        //b.insert(t[7]);
        //b.decreaseKey(2,2,t);
        //b.decreaseKey(3,3,t);
        //b.decreaseKey(4,4,t);
        //b.decreaseKey(6,6,t);

        while( !b.isEmpty()){
            int x = b.removeMin();
            System.out.println(t[x].vertex + "--" + t[x].key);
        }
    }
}
