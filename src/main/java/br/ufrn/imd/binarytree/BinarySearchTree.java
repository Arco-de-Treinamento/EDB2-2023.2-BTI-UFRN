package br.ufrn.imd.binarytree;

public class BinarySearchTree {
    Node root;
    
    private boolean searchRecursive(Node node, int key) {
        if(node == null) {
            return false;
        } else if (node.key == key){
            return true;
        } else if(node.key > key) {
            return searchRecursive(node.left, key);
        } else {
            return searchRecursive(node.right, key);
        }
    }
    
    private Node insertRecursive(Node node, int key) {
        if(node == null) {
            return new Node(key);
        } else if(node.key > key) {
            node.left = insertRecursive(node.left, key);
        } else {
            node.right = insertRecursive(node.right, key);
        }
        
        return node;
    }
    
    private int findSmallestRecursive(Node node) {
        if(node.left == null) {
            return node.key;
        } else {
            return findSmallestRecursive(node.left);
        }
    }

    private Node removeRecursive(Node node, int key) {
        if(node == null) {
            return node;
        } else if (node.key == key){
            //Caso 1 de remocao: sem filhos
            if(node.left == null && node.right == null) {
                return null;
            } else if(node.left == null) {
                return node.right;
            } else if(node.right == null) {
                return node.left;
            } else {
                int smallest = findSmallestRecursive(node.right);

                node.key = smallest;
                node.right = removeRecursive(node.right, smallest);

                return node;
            }
        } else if(node.key > key) {
            node.left = removeRecursive(node.left, key);
            return node;

        } else {
            node.right = removeRecursive(node.right, key);
            return node;
        }
    }
    
    public void insert(int key) {
        root = insertRecursive(root, key);
    }
    
    public void remove(int key) {
        root = removeRecursive(root, key);
    }
    
    public boolean search(int key) {
        return searchRecursive(root, key);
    }
}

