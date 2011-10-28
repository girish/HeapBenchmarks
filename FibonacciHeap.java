class Node {
        int key;

        int rank;// # of childer.

        boolean tagged;// critical one(r_j==j-2) will be tagged

        Node parent;

        Node child;

        Node left_sibling;

        Node right_sibling;

        public Node(int key) {
                this.key = key;
                left_sibling = this;
                right_sibling = this;
                parent = null;
                child = null;
                rank = 0;
                tagged = false;
        }

        /**
         * 
         * @param x
         *            x is not the only child
         */
        public void removeFromFamiliy(Node p) {
                Node u = this.left_sibling;
                Node w = this.right_sibling;
                u.right_sibling = w;
                w.left_sibling = u;
                if (p.child == this) {
                        p.child = w;
                }
        }

        /**
         * if this is the root of double linked list
         * 
         * @param x
         */
        public void addNode(Node x) {
                // suppose there are two or more than two nodes in list.
                // after get the code, verify it works when there is only one node
                Node u = this.left_sibling;
                x.right_sibling = this;
                x.left_sibling = u;
                this.left_sibling = x;
                u.right_sibling = x;

                x.parent = null;
        }

        public Node getChild() {
                return child;
        }
        
        public void printTree(){
                //NodeUtil.printTree(this);
        } 

        /**
         * How many children the Node have. based on degree, we can know the tree
         * size(2^degree)
         * 
         * @return
         */
        public int getDegree() {
                if (child == null) {
                        return 0;
                } else {
                        int count = 0;
                        Node t = child;
                        do {
                                count++;
                                t = t.right_sibling;
                        } while (t != null & t != child);
                        return count;
                }
        }

        public Node getSibling() {
                return this.right_sibling;
        }
        
        public String toString() {
                return "[" + key + "; " + getDegree() + "; c="
                                + (child == null ? "" : "" + child.key) + "s="
                                + (right_sibling == null ? "" : "" + right_sibling.key) + "]";
        }
}



public class FibonacciHeap {
        Node root;// point to the smallest one

        /**
         * only key in x is set up already
         * 
         * @param x
         */
        public void insert(Node x) {
                if (root == null) {
                        root = x;
                } else {
                        root.addNode(x);
                }
                if (x.key < root.key) {
                        root = x;
                }
        }

        /**
         * 
         * @param v
         * @param newKey
         *            less than or equall to original key
         */
        public void decreaseKey(Node v, int newKey) {
                v.key = newKey;
                Node p = v.parent;
                if (p == null) {// in root list
                        if (root.key > newKey) {
                                root = v;
                        }
                } else if (p.key > newKey) {
                        while (true) {
                                if (p.rank >= 2) {
                                        v.removeFromFamiliy(p);
                                } else {
                                        // no need t to remove from family.
                                        p.child = null;
                                }
                                root.addNode(v);
                                if (v.key < root.key) {
                                        root = v;
                                }
                                // case 1: remove child from root.
                                Node pp = p.parent;
                                if (pp == null) {
                                        p.rank -= 1;
                                        break;
                                }
                                // case 2: remove from untagged parent, just remove it and tag
                                // it.
                                if (p.tagged == false) {
                                        p.rank -= 1;
                                        p.tagged = true;
                                        break;
                                } else {// case 3: tagged parent, need recursive removing.
                                        p.rank -= 1;
                                        v = p;
                                        p = pp;
                                }

                        }
                }
        }

        public Node extractMin() {
                if (root == null) {
                        return null;
                }
                Node res = root;
                Node v, w;
                if (root.rank < 1) {// no child
                        v = root.right_sibling;
                } else {
                        w = root.child;
                        v = w.right_sibling;
                        w.right_sibling = root.right_sibling;//tricky, the middle state.
                        for (w = v; w != root.right_sibling; w = w.right_sibling) {
                                w.parent = null;
                        }
                        while(v!=root){
                                w=v.right_sibling;
                                //addNodeToA(v);
                                v=w;
                                
                        }

                }
                return res;
        }

        /**
         * 
         * @param head
         */
        public void printRootList() {// OutputStream out
                if (root == null) {
                        return;
                }
                Node t = root;
                System.out.print("Tree: [");
                do {
                        System.out.print(t.key + ",");
                        t = t.right_sibling;
                } while (t != root);
                System.out.println("]");
        }

        public void print() {
                if (root == null)
                        return;
                Node t = root;
                do {
                        printTree(t);
                        t = t.right_sibling;
                } while (t != root);
                // for(Node t=root; t)
        }

        public void printTree(Node head) {// OutputStream out
                head.printTree();
        }
        public static void main(String[] args) {

        FibonacciHeap fh = new FibonacciHeap();

                Node[] nodes = new Node[10];
                for (int i = 0; i < 10; i++) {
                        System.out.println();
                        System.out.println("i=" + i);
                        nodes[i] = new Node(i);
                        fh.insert(nodes[i]);
                        fh.printRootList();
                        fh.print();
                }

                for (int i = 5; i < 10; i++) {
                        System.out.println();
                        System.out.println("i=" + i);
                        fh.decreaseKey(nodes[i], i - 5);
                        fh.printRootList();
                        fh.print();
                }

                for (int i = 0; i < 5; i++) {
                        System.out.println("minNode=" + fh.extractMin());
                        fh.print();
                }

        }
}