/* COdigo inicialmente disponivel em:
     * Red-Black Tree
     * link: https://www.programiz.com/dsa/red-black-tree
 */

package br.ufrn.imd.redblacktree;

public class RedBlackTree {
    private Node root;
    private Node TNULL;
    static final boolean RED = true;
    static final boolean BLACK = false;

    private void preOrderHelper(Node node){
        if(node != TNULL){
            preOrderHelper(node.left);
            preOrderHelper(node.right);
        }
    }

    private void inOrderHelper(Node node){
        if (node != TNULL){
            inOrderHelper(node.left);
            inOrderHelper(node.right);
        }
    }

    private void postOrderHelper(Node node){
        if (node != TNULL){
            postOrderHelper(node.left);
            postOrderHelper(node.right);
        }
    }

    private Node searchTreeHelper(Node node, int key){
        if(node == TNULL || key == node.key){
            return node;
        }
        if(key < node.key){
            return searchTreeHelper(node.left, key);
        } else {
            return searchTreeHelper(node.right, key);
        }
    }

    private void fixDelete(Node x){
        Node s;

        while (x != root && x.color == BLACK){
            if(x == x.parent.left){
                s = x.parent.right;
                if(s.color == RED){
                    s.color = BLACK;
                    x.parent.color = RED;
                    leftRotate(x.parent);
                    s = x.parent.right;
                }

                if(s.left.color == BLACK && s.right.color == BLACK){
                    s.color = RED;
                    x = x.parent;
                } else {
                    if(s.right.color == BLACK){
                        s.left.color = BLACK;
                        s.color = RED;
                        rightRotate(s);
                        s = x.parent.right;
                    }
                    s.color = x.parent.color;
                    x.parent.color = BLACK;
                    s.right.color = BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            }else{
                s = x.parent.left;
                if(s.color == RED){
                    s.color = BLACK;
                    x.parent.color = RED;
                    rightRotate(x.parent);
                    s = x.parent.left;
                }
                if(s.left.color == BLACK && s.right.color == BLACK){
                    s.color = RED;
                    x = x.parent;
                } else {
                    if(s.left.color == BLACK){
                        s.right.color = BLACK;
                        s.color = RED;
                        leftRotate(s);
                        s = x.parent.left;
                    }
                    s.color = x.parent.color;
                    x.parent.color = BLACK;
                    s.left.color = BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = BLACK;
    }

    private void rbTransplant(Node u, Node v){
        if(u.parent == null){
            root = v;
        } else if (u == u.parent.left){
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    private void deleteNodeHelper(Node node, int key){
        Node z = TNULL;
        Node x;
        Node y;

        while (node != TNULL) {
            if (node.key == key) {
                z = node;
            }

            if(node.key <= key){
                node = node.right;
            } else {
                node = node.left;
            }
        }

        if(z == TNULL) return;

        y = z;
        boolean y_original_color = y.color;

        if(z.left == TNULL){
            x = z.right;
            rbTransplant(z, z.right);
        } else if(z.right == TNULL){
            x = z.left;
            rbTransplant(z, z.left);
        } else {
            y = minimum(z.right);
            y_original_color = y.color;
            x = y.right;
            if(y.parent == z){
                x.parent = y;
            } else {
                rbTransplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }

        if(y_original_color == BLACK){
            fixDelete(x);
        }
    }

    private void fixInsert(Node k){
        Node u;

        while (k.parent.color == RED){
            if(k.parent == k.parent.parent.right){
                u = k.parent.parent.left;
                if(u.color == RED){
                    u.color = BLACK;
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if(k == k.parent.left){
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right;

                if(u.color == RED){
                    u.color = BLACK;
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if(k == k.parent.right){
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    rightRotate(k.parent.parent);
                }
            }

            if(k == root){
                break;
            }
        }
        root.color = BLACK;
    }

    public RedBlackTree(){
        TNULL = new Node(0);
        TNULL.color = BLACK;
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }

    public Node getRoot(){
        return root;
    }

    public void preOrder(){
        preOrderHelper(root);
    }

    public void inOrder(){
        inOrderHelper(root);
    }

    public void postOrder(){
        postOrderHelper(root);
    }

    public void searchTree(int key){
        searchTreeHelper(root, key);
    }

    public Node minimum(Node node){
        while(node.left != TNULL){
            node = node.left;
        }
        return node;
    }

    public Node maximum(Node node){
        while(node.right != TNULL){
            node = node.right;
        }
        return node;
    }

    public Node sucessor(Node node){
        if(node.right != TNULL){
            return minimum(node.right);
        }

        Node y = node.parent;
        while(y != TNULL && node == y.right){
            node = y;
            y = y.parent;
        }
        return y;
    }

    public Node predecessor(Node node){
        if(node.left != TNULL){
            return maximum(node.left);
        }

        Node y = node.parent;
        while(y != TNULL && node == y.left){
            node = y;
            y = y.parent;
        }

        return y;
    }

    public void leftRotate(Node node){
        Node y = node.right;
        node.right = y.left;

        if(y.left != TNULL){
            y.left.parent = node;
        }

        y.parent = node.parent;

        if(node.parent == null){
            root = y;
        } else if(node == node.parent.left){
            node.parent.left = y;
        } else {
            node.parent.right = y;
        }

        y.left = node;
        node.parent = y;
    }

    public void rightRotate(Node node){
        Node y = node.left;
        node.left = y.right;

        if(y.right != TNULL){
            y.right.parent = node;
        }

        y.parent = node.parent;

        if(node.parent == null){
            root = y;
        }else if(node == node.parent.right){
            node.parent.right = y;
        }else{
            node.parent.left = y;
        }

        y.right = node;
        node.parent = y;
    }

    public void insert(int key){
        Node node = new Node(key);
        node.parent = null;
        node.left = TNULL;
        node.right = TNULL;

        Node y = null;
        Node x = this.root;

        while(x != TNULL){
            y = x;
            if(node.key < x.key){
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;

        if(y == null){
            root = node;
        }else if(node.key < y.key){
            y.left = node;
        } else {
            y.right = node;
        }

        if(node.parent == null){
            node.color = BLACK;
            return;
        }

        if(node.parent.parent == null){
            return;
        }

        fixInsert(node);
    }

    public void deleteNode(int key){
        deleteNodeHelper(this.root, key);
    }
}
