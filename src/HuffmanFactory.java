import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

public class HuffmanFactory {
    private final Writer writer;
    public HuffmanFactory(Writer writer) {
        this.writer = writer;
    }
    public void run(byte[] text) {
        String str = new String(text); //just to convert from byte[] to char[] (implicitly)
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
