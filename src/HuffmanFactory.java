import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

public class HuffmanFactory {
    private final Writer writer;
    //private ConfigParser config = null;
    //private HashMap<Character, byte[]> codesMap = null;
    //private HashMap<Character, Integer> frequencies = new HashMap<>();
    //private Tree tree = null;
    public HuffmanFactory(Writer writer) {
        this.writer = writer;
    }
    public void run(byte[] text) {
        String str = new String(text);
        HashMap<Character, Integer> frequencies = countFrequencies(str);
        writer.writeTreeMetainfo(frequencies);
        Tree tree = new Tree(frequencies);
        HashMap<Character, byte[]> codeTable = tree.getCharCodes();
        byte[] encodedText = encodeText(str, codeTable);
        writer.writeEncodedChars(encodedText);
    }
    private HashMap<Character, Integer> countFrequencies(String text) {
        HashMap<Character, Integer> frequencies = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            Integer freq = frequencies.getOrDefault(c, 0);
            frequencies.put(c, ++freq);
        }
        return frequencies;
    }
    /*public void buildTree() {
        tree = new Tree(frequencies);
        codesMap = null;
    }*/
    /*private HashMap<Character, byte[]> getCodeTable() {
        if (codesMap != null) {
            return codesMap;
        }
        codesMap = tree.getCharCodes();
        return codesMap;
    }*/
    /*public void printCodesMetainfo() {
        //BareTree bt = new BareTree(tree.head());
        try {
            FileOutputStream fos = new FileOutputStream(config.outputPath());
            ObjectOutputStream oos = new ObjectOutputStream(fos);

        }
        catch (IOException e) {
            System.out.println("Unable to write codes meta-info in file");
        }
    }*/
    public byte[] encodeText(String text, HashMap<Character, byte[]> codeTable) {
        int bSize = 0;
        for(int i = 0; i < text.length(); i++) {
            bSize += codeTable.get(text.charAt(i)).length;
        }
        byte[] encodedText = new byte[bSize];
        byte[] encodedChar = null;
        int j = 0;
        for(int i = 0; i < text.length(); i++) {
            encodedChar = codeTable.get(text.charAt(i));
            for (byte b : encodedChar) {
                encodedText[j] = b;
                j++;
            }
        }
        return encodedText;
    }
}
