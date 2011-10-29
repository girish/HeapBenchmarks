
class Bnode
{
    /** The object stored in this node. */
    public int key;

    public int vertex;

    /** This node's parent, or <code>null</code> if this node is a
     * root. */
    public Bnode p;

    /** This node's leftmost child, or <code>null</code> if this
     * node has no children. */
    public Bnode child;

    /** This node's right sibling, or <code>null</code> if this
     * node has no right sibling. */
    public Bnode sibling;

    /** the number of children that this node has. */
    public int degree;

    /** Creates New node **/
    public Bnode(int vertex, int weight)
    {
        key = weight;
        this.vertex = vertex;
        p = null;
        child = null;
        sibling = null;
        degree = 0;
    }
}

