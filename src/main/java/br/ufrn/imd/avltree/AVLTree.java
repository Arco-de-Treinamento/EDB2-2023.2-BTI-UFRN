package br.ufrn.imd.avltree;

public class AVLTree {
    private Node root;

    private void updateHeight(Node node) {
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    private int getHeight(Node node) {
        if(node == null) {
            return -1;
        } else {
            return node.height;
        }
    }

    private Node findSmallestRecursive(Node node) {
        if(node.left == null) {
            return node;
        } else {
            return findSmallestRecursive(node.left);
        }
    }

    private Node rotateLeft(Node node){
        Node right = node.right;
        Node left = right.left;

        right.left = node;
        node.right = left;

        updateHeight(node);
        updateHeight(right);

        return right;
    }

    private Node rotateRight(Node node){
        Node left = node.left;
        Node right = left.right;

        left.right = node;
        node.left = right;

        updateHeight(node);
        updateHeight(left);

        return left;
    }

    private Node doubleRotateLeft(Node node){
        node.right = rotateRight(node.right);
        return rotateLeft(node);
    }

    private Node doubleRotateRight(Node node){
        node.left = rotateLeft(node.left);
        return rotateRight(node);
    }

    private Node rebalance(Node node){
        updateHeight(node);
        int balanceFactor = getBalanceFactor(node);

        if(balanceFactor > 1){
            if(getHeight(node.right.right) > getHeight(doubleRotateLeft(node.right))) {
                node = rotateLeft(node);
            } else {
                node.right = rotateRight(node.right);
                node = rotateLeft(node);
            }
        } else if (balanceFactor < -1) {
            if(getHeight(node.left.left) > getHeight(doubleRotateRight(node.left))) {
                node = rotateRight(node);
            } else {
                node.left = rotateLeft(node.left);
                node = rotateRight(node);
            }
        } 

        return node;
    }

    private Node insertRecursive(Node node, int key){
        if(node == null){
            return new Node(key);
        } else if (node.key > key){
            node.left = insertRecursive(node.left, key);
        } else if (node.key < key){
            node.right = insertRecursive(node.right, key);
        }

        return rebalance(node);
    }

    private Node removeRecursive(Node node, int key){
        if(node == null){
            return new Node(key);
        } else if (node.key > key){
            node.left = removeRecursive(node.left, key);
        } else if (node.key < key){
            node.right = removeRecursive(node.right, key);
        } else {
            if(node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                Node smallest = findSmallestRecursive(node.right);

                node.key = smallest.key;
                node.right = removeRecursive(node.right, smallest.key);
            }
        }

        if (node != null) {
            node = rebalance(node);
        }

        return node;
    }

    private boolean searchRecursive(Node node, int key){
        if(node == null){
            return false;
        } else if (node.key == key){
            return true;
        } else if(node.key > key) {
            return searchRecursive(node.left, key);
        } else {
            return searchRecursive(node.right, key);
        }
    }

    public int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        } else {
            return getHeight(node.left) - getHeight(node.right);
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
