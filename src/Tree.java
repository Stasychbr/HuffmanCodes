import java.nio.ByteBuffer;
import java.util.Arrays;
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
    private final Node head;
    public Tree(HashMap<Character, Integer> frequencies) {
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::value));
        frequencies.forEach((ch, freq) ->nodePriorityQueue.add(new Node(ch, freq)));
            //System.out.println("'" + ch + "': " + freq);}); //dbg
        //System.out.println("-------------------"); //dbg
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
    public HashMap<Character, byte[]> getCharCodes() {
        HashMap <Character, Integer> encodingMap = new HashMap<>();
        getCharCodes(encodingMap, head, 1);
        HashMap<Character, byte[]> result = new HashMap<>();
        encodingMap.forEach((Character ch, Integer code)->{
            byte[] toPut = ByteBuffer.allocate(Integer.BYTES).putInt(code).array();
            int bitsNum = Integer.SIZE - Integer.numberOfLeadingZeros(code);
            int bytesNum = bitsNum % Byte.SIZE == 0 ? bitsNum / Byte.SIZE : bitsNum / Byte.SIZE + 1;
            toPut = Arrays.copyOfRange(toPut, toPut.length - bytesNum, toPut.length);
            result.put(ch, toPut);
        });
        //result.forEach((Character ch, byte[] bArr)-> System.out.println(ch + Arrays.toString(bArr))); /dbg
        return result;
    }
}

