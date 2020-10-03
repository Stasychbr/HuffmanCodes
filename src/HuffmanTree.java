import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;


public class HuffmanTree {
    private class Node {
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

    private Node mergeNodes(Node left, Node right) {
        Node newHead = new Node();
        newHead.addLeft(left);
        newHead.addRight(right);
        return newHead;
    }
    private Node head = null;
    private ConfigParser config = null;
    public HuffmanTree(ConfigParser config) {
        this.config = config;
    }
    public boolean buildTree() {
        if (config == null) {
            return false;
        }
        BufferedReader br = new BufferedReader(config.inputFile(), config.bufSize());
        HashMap<Character, Integer> frequencies = new HashMap<>();
        try {
            while (br.ready()) {
                Character c = (char)br.read();
                Integer freq = frequencies.getOrDefault(c, 0);
                frequencies.put(c, ++freq);
            }
            br.close();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::value));
        frequencies.forEach((ch, freq) -> {nodePriorityQueue.add(new Node(ch, freq));
        System.out.println("'" + ch + "': " + freq);});
        System.out.println("-------------------");
        while (nodePriorityQueue.size() > 1) {
            Node node1 = nodePriorityQueue.poll();
            Node node2 = nodePriorityQueue.poll();
            nodePriorityQueue.add(mergeNodes(node1, node2));
        }
        head = nodePriorityQueue.poll();
        return true;
    }
    /*public class EncodedChar {
        private char ch;
        private int code;
        private EncodedChar(char ch, int code) {
            this.ch = ch;
            this.code = code;
        }
        public int getCode() {return code;}
        public char getCh() {return ch;}
    }*/
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
    public HashMap<Character, Integer> getCodeTable() {
        HashMap<Character, Integer> codesMap = new HashMap<>();
        getCharCodes(codesMap, head, 1);
        codesMap.forEach((Character ch, Integer i)-> codesMap.replace(ch, i & ~Integer.highestOneBit(i)));
        return codesMap;
    }
}
