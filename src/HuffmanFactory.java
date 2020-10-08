import java.io.*;
import java.util.*;

public class HuffmanFactory {
    private ConfigParser config = null;
    private HashMap<Character, String> codesMap = null;
    private Tree tree = null;
    public HuffmanFactory(ConfigParser config) {
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
        tree = new Tree(frequencies);
        codesMap = null;
        return true;
    }
    public HashMap<Character, String> getCodeTable() {
        if (codesMap != null) {
            return codesMap;
        }
        codesMap = tree.getCharCodes();
        return codesMap;
    }
    public void printCodesTree() {
        BareTree bt = new BareTree(tree.head());
        try {
            FileOutputStream fos = new FileOutputStream("tree.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(bt);
            oos.flush();
            oos.close();
        }
        catch (IOException e) {
            System.out.println("kek");
        }
    }
    public void parseTree() {
        try {
            FileInputStream fis = new FileInputStream("tree.txt");
            ObjectInputStream oin = new ObjectInputStream(fis);
            BareTree test = (BareTree) oin.readObject();
            System.out.println("parsed");
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("kek");
        }
    }
}
