import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

class Node {
    private Node left = null;
    private Node right = null;
    private Character ch = null;
    private Integer value = 0;
    public Node left() { return left;}
    public Node right() { return right;}
    public int value() {return value;}
    public Character ch() {return ch;}
    public Node(Character letter, Integer freq) {
        ch = letter;
        value = freq;
    }
    public Node(){};
    public void addLeft(Node node) {
        left = node;
        value += node.value;
    }
    public void addRight(Node node) {
        right = node;
        value += node.value;
    }
}

public class Tree {
    public Node mergeNodes(Node left, Node right) {
        Node newHead = new Node();
        newHead.addLeft(left);
        newHead.addRight(right);
        return newHead;
    }
    private Node head = null;
    public Node head() {return head;}
    public Tree(HashMap<Character, Integer> frequencies) {
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::value));
        frequencies.forEach((ch, freq) ->{ nodePriorityQueue.add(new Node(ch, freq));
            System.out.println("'" + ch + "': " + freq);});
        System.out.println("-------------------");
        while (nodePriorityQueue.size() > 1) {
            Node node1 = nodePriorityQueue.poll();
            Node node2 = nodePriorityQueue.poll();
            nodePriorityQueue.add(mergeNodes(node1, node2));
        }
        head = nodePriorityQueue.poll();
    }
    private void getCharCodes(HashMap<Character, Integer> map, Node node, int curCode) {
        if (node == null) {
            return;
        }
        getCharCodes(map, node.left(), curCode << 1);
        getCharCodes(map, node.right(), (curCode << 1) | 1);
        if (node.ch() != null) {
            map.put(node.ch(), curCode);
        }
    }
    public HashMap<Character, Integer> getCharCodes() {
        HashMap <Character, Integer> encodingMap = new HashMap<>();
        getCharCodes(encodingMap, head, 1);
        //HashMap<Character, String> resMap = new HashMap<>();
        //encodingMap.forEach((Character ch, Integer i)-> resMap.put(ch, Integer.toBinaryString(i).substring(1)));
        return encodingMap;
    }
}

