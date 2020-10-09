import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

public class HuffmanFactory {
    private Writer writer = null;
    private ConfigParser config = null;
    private HashMap<Character, Integer> codesMap = null;
    private HashMap<Character, Integer> frequencies = new HashMap<>();
    private Tree tree = null;
    public HuffmanFactory(ConfigParser config) {
        this.config = config;
    }
    public void countFrequencies(byte[] text) {
        String curStr = new String(text);
        for (int i = 0; i < curStr.length(); i++) {
            char c = curStr.charAt(i);
            Integer freq = frequencies.getOrDefault(c, 0);
            frequencies.put(c, ++freq);
        }
    }
    public void buildTree() {
        tree = new Tree(frequencies);
        codesMap = null;
    }
    public HashMap<Character, Integer> getCodeTable() {
        if (codesMap != null) {
            return codesMap;
        }
        codesMap = tree.getCharCodes();
        return codesMap;
    }
    public void printCodesTree() {
        BareTree bt = new BareTree(tree.head());
        try {
            FileOutputStream fos = new FileOutputStream(config.treePath());
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

    }
    public void encodeText() {
        try {
            FileOutputStream fos = new FileOutputStream(config.outputPath());
            FileReader fr = new FileReader(config.inputPath());
            StringBuilder curPiece = new StringBuilder();
            String mod = "";
            while (fr.ready()) {
                char c = (char)fr.read();
                Integer curCode = codesMap.get(c);
                String curStr = Integer.toBinaryString(curCode);
                String toAppend = curStr.substring(0, Math.min(curStr.length(), Long.SIZE - curPiece.length()));
                curPiece.append(mod);
                curPiece.append(toAppend);
                if (toAppend.length() == curStr.length()) {
                    mod = "";
                }
                else {
                    mod = curStr.substring(toAppend.length(), curStr.length());
                }
                if (curPiece.length() == Long.SIZE) {
                    long num = Long.parseLong(curPiece.toString(), 2);
                    fos.write(ByteBuffer.allocate(Long.BYTES).putLong(num).array());
                    curPiece.setLength(0);
                    curPiece.append(mod);
                }
            }
            long num = Long.parseLong(curPiece.toString(), 2);
            int bitNum = Long.SIZE - Long.numberOfLeadingZeros(num);
            byte[] bArr = ByteBuffer.allocate(Long.BYTES).putLong(num).array();
            bArr = Arrays.copyOfRange(bArr, bArr.length - (bitNum / 8 + 1), bArr.length);
            fos.write(bArr);
            System.out.println("Encoded");
        }
        catch (IOException e) {
            System.out.println("Error during text encoding");
        }
    }
}
