public class ssp {
    public static void main(String[] args){
        Graph G=new Graph(15);
        G.RandomGraph();
        G.print();
        Dsp d = new Dsp(G, 1);
        d.print();
    }
}