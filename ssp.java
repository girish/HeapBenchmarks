import java.io.*;

public class ssp {
   Graph G;

   int size;

   public void initGraph(String[] args){
        if (args[0].charAt(1) == 'r') {
            //random mode
            System.out.println("Random mode");
        }
        if (args[0].charAt(1) == 'i'){
            System.out.println("Interactive mode");
            scanInput(args);
            if (args[0].charAt(2) == 'b'){
                System.out.println("BinomialHeapTest");
            }
            if (args[0].charAt(2) == 'f'){
                System.out.println("FibonacciHeapTest");
            }
            if (args[0].charAt(2) == 's'){
                System.out.println("SimpleSchemeTest");
            }
        }
       G = new Graph(0);
   }

   public void scanInput(String[] args){
        if (args.length == 2) {
            //scan from file
        }
        else{
            //scan from stdin;
        }

        while(true){
          //read line by line;
          break;
        }
   }

    public static void main(String[] args){
        /*
        if(args.length==0){
            System.out.println("Need atleast one of these arguments");
            return;
        }
        ssp S = new ssp();
        S.initGraph(args);

        */
        //System.out.println(args[0]);
        Graph G=new Graph(15);
        G.RandomGraph();
        G.print();
        Dsp d = new Dsp(G, 1);
        d.print();
    }
}