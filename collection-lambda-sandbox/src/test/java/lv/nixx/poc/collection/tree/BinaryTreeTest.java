package lv.nixx.poc.collection.tree;

import org.junit.Test;

public class BinaryTreeTest {

    @Test
    public void operationsWithTree() {
        BinaryTree bt = new BinaryTree();

        bt.add(6);
        bt.add(4);
        bt.add(8);
        bt.add(3);
        bt.add(5);
        bt.add(7);
        bt.add(9);

        new TreePrinter().traverseInOrder(bt);

        System.out.println("===============");

        bt.traverseLevelOrder();

    }

}
