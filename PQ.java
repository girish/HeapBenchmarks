
interface PQ {
    public int delMin();
    public void decreaseKey(int vertex, int newValue);
    public void insert(int vertex, int weight);
    public boolean isEmpty();
    public boolean contains(int vertex);
}
