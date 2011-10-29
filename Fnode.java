class Fnode
{
    int data;

    Fnode child;

    /**
     * left sibling node
     */
    Fnode left;

    /**
     * parent node
     */
    Fnode parent;

    /**
     * right sibling node
     */
    Fnode right;

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

    public Fnode(int data, int key)
    {
        right = this;
        left = this;
        this.data = data;
        this.key = key;
    }

}

