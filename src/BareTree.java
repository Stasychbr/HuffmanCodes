import java.io.Serializable;

class BareNode implements Serializable {
    Character ch = null;
    BareNode left = null;
    BareNode right = null;
    static BareNode copySubTree(Node node) {
        if (node == null) {
            return null;
        }
        BareNode curNode = new BareNode();
        curNode.ch = node.ch();
        curNode.left = copySubTree(node.left());
        curNode.right = copySubTree(node.right());
        return curNode;
    }
}

public class BareTree implements Serializable {
    BareNode head;
    public BareTree(Node head) {
        this.head = BareNode.copySubTree(head);
    }
}