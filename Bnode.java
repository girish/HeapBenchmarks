
class Bnode
{
    //key to min
    public int key;

    // data for vertex
    public int vertex;

    //parent node
    public Bnode p;

    //pointer to first child
    public Bnode child;

    //pointer to sibling
    public Bnode sibling;

    //no of children
    public int degree;

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

