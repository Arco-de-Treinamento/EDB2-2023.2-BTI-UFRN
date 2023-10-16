package br.ufrn.imd.redblacktree;

public class RedBlackTree {
    private Node root;

    static final boolean RED = false;
    static final boolean BLACK = true;

    private static class NilNode extends Node {
        private NilNode() {
            super(0);
            this.color = BLACK;
        }
    }

    private void replaceParent(Node parent, Node oldChild, Node newChild){
        if(parent != null){
            if (parent.left == oldChild) {
                parent.left = newChild;
            } else {
                parent.right = newChild;
            }
        } else {
            root = newChild;
        }

        newChild.parent = parent;
    }

    private Node removeNodeWithoutParentChild(Node node){
        if(node.left != null){
            replaceParent(node.parent, node, node.left);
            return node.left;
        } else if(node.right != null){
            replaceParent(node.parent, node, node.right);
            return node.right;
        } else {
            replaceParent(node.parent, node, (node.color == BLACK) ? new NilNode() : null);
            return null;
        }
    }

    private Node getUncle(Node parent){
        Node grandparent = parent.parent;

        if(grandparent.left == parent){
            return grandparent.right;
        } else {
            return grandparent.left;
        }
    }

    private void rotateLeft(Node node){
        Node parent = node.parent;
        Node right = node.right;

        node.right = right.left;
        if(right.left != null){
            right.left.parent = node;
        }

        right.left = node;
        node.parent = right;

        replaceParent(parent, node, right);
    }

    private void rotateRight(Node node){
        Node parent = node.parent;
        Node left = node.left;

        node.left = left.right;
        if(left.right != null){
            left.right.parent = node;
        }

        left.right = node;
        node.parent = left;

        replaceParent(parent, node, left);
    }

    private void fixRedBlack(Node node){
        Node parent = node.parent;
        Node grandparent = node.parent.parent;
        Node uncle = getUncle(parent);

        if(parent == null || parent.color == BLACK){
            return;
        } else if (grandparent == null){
            parent.color = BLACK;
            return;
        } else if (uncle != null && uncle.color == RED){
            parent.color = BLACK;
            uncle.color = BLACK;
            grandparent.color = RED;

            fixRedBlack(grandparent);
        } else if (parent == grandparent.left){
            if(parent.right == node){
                rotateLeft(parent);
                parent = node;
            }

            rotateRight(grandparent);
            parent.color = BLACK;
            grandparent.color = RED;
        } else {
            if (node == parent.left){
                rotateRight(parent);
                parent = node;
            }

            rotateLeft(grandparent);
            parent.color = BLACK;
            grandparent.color = RED;
        }
    }

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
            Node newNode = new Node(key);
            newNode.color = RED;
            
            return newNode;
        } else if(node.key > key) {
            node.left = insertRecursive(node.left, key);
        } else {
            node.right = insertRecursive(node.right, key);
        }
        
        return node;
    }

    private Node removeRecursive(Node node, int key) {
        if(node == null) {
            return node;
        } else if (node.key == key){
            // implementar remoção
            return node;
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
        fixRedBlack(root);
    }

    public boolean search(int key) {
        return searchRecursive(root, key);
    }

    public void remove(int key) {
        root = removeRecursive(root, key);
    }
}
