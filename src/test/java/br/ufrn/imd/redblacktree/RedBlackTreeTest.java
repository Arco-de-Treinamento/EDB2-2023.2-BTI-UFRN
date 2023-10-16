package br.ufrn.imd.redblacktree;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RedBlackTreeTest {
    @Test
    public void testInsert() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);

        assertTrue(tree.search(5));
        assertTrue(tree.search(3));
        assertTrue(tree.search(7));
        assertFalse(tree.search(10));
    }

    @Test
    public void testRemove() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);

        tree.remove(5);
        assertFalse(tree.search(5));
        assertTrue(tree.search(3));
        assertTrue(tree.search(7));

        tree.remove(3);
        assertFalse(tree.search(3));
        assertTrue(tree.search(7));

        tree.remove(7);
        assertFalse(tree.search(7));
    }

    @Test
    public void testSearch() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);

        assertTrue(tree.search(5));
        assertTrue(tree.search(3));
        assertTrue(tree.search(7));
        assertFalse(tree.search(10));
    }
}
