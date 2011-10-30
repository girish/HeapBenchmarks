import java.io.*;
import java.util.*;

public class ssp {

    Graph G;


    /** Helpers for user mode Graph Initialization

     Vertices count; Intialized by the end of
     reading from user mode
    */
    int vCount;

    int eCount; //edge count.

    int[][] e;  //temp storage for edges;

    public ssp(){
        vCount = 0;
        eCount = 0;
        //max  500 lines of input while running in
        //user mode
        e = new int[500][3];

    }

    public void initGraph(String[] args){
        if (args[0].charAt(1) == 'r') {
            //random mode
            statsForRandomGraph();
            System.out.println("Random mode");
        }
        if (args[0].charAt(1) == 'i'){
            System.out.println("Interactive mode");
            scanInput(args);

            G = new Graph(vCount+1);
            for(int i = 0; i<eCount; i++)
                G.addEdge(e[i][0],e[i][1],e[i][2]);
            G.print();

            int[][] d;
                d = G.calculateDspForAll(1);

            if (args[0].charAt(2) == 'b'){
                //System.out.println("BinomialHeapTest");
                d = G.calculateDspForAll(1);
            }
            if (args[0].charAt(2) == 'f'){
                //System.out.println("FibonacciHeapTest");
                d = G.calculateDspForAll(2);
            }
            if (args[0].charAt(2) == 's'){
                //System.out.println("SimpleSchemeTest");
                d = G.calculateDspForAll(0);
            }
            for(int i=0; i< d.length;i++){
                for(int j=0; j< d.length;j++)
                    if(d[i][j] == Graph.INFINITY)
                        System.out.print("? ");
                    else
                        System.out.print(d[i][j]+"  ");
                System.out.println("");
            }
        }
    }

    public void statsForRandomGraph(){
        long[][][] m = new long[6][11][3];
        for(int i=1;i<6;i++)
            for(int j=1;j<11;j++){
                Graph G = new Graph(i*100);
                G.RandomGraph(j*10);
                m[i][j][0] = G.profileDspForAll(0);
                m[i][j][1] = G.profileDspForAll(1);
                m[i][j][2] = G.profileDspForAll(2);
            }
        for(int i=1;i<6;i++)
            for(int j=1;j<11;j++){
                System.out.println(i*100+"  " +j*10+"  "+ m[i][j][0]+ "  "+ m[i][j][1]+"  "+m[i][j][2]);
            }
    }

    public void scanInput(String[] args) {

        Scanner s = new Scanner(new BufferedInputStream(System.in));
        if (args.length == 2) {
            //scan from file
            try {
                s = new Scanner(new BufferedReader(new FileReader(args[1])));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

        while(s.hasNextInt()){
            e[eCount][0] = s.nextInt();
            e[eCount][1] = s.nextInt();
            e[eCount][2] = s.nextInt();
            if (Math.max(e[eCount][0],e[eCount][1]) > vCount)
                vCount = Math.max(e[eCount][0],e[eCount][1]);
            eCount++;
        }
    }

    public static void main(String[] args){
        if(args.length==0){
            System.out.println("Need atleast one of these arguments");
            return;
        }
        ssp S = new ssp();
        S.initGraph(args);

        //System.out.println(args[0]);
        //Graph G=new Graph(15);
        //G.RandomGraph();
        //G.print();
        //Dsp d = new Dsp(G, 1, 0);
        //d.print();
    }
}