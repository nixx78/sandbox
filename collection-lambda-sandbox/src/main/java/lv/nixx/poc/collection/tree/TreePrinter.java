package lv.nixx.poc.collection.tree;

public class TreePrinter {

    private final StringBuilder sb = new StringBuilder();

    public void traverseInOrder(BinaryTree binaryTree) {
        Node root = binaryTree.getRoot();

        sb.append("Root:").append(root.value).append("\n");
        traverseInOrder(root);

        System.out.println(sb.toString());
    }

    private void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            if (node.value != null) {
                sb.append(node).append("\n");
            }
            traverseInOrder(node.right);
        }
    }

}
