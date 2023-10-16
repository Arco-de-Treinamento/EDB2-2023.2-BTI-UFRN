package br.ufrn.imd.redblacktree;

class Node {
    int key;
    boolean color;

    Node left;
    Node right;
    Node parent;
    
    public Node(int key) {
        this.key = key;
    }
}
