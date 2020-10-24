import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

public class Decoder {
    FileInputStream encoded;
    //FileReader origin;
    FileOutputStream decoded;
    Decoder(ConfigParser config) {
        try {
            encoded = new FileInputStream(config.outputPath());
            //origin = new FileReader(config.inputPath());
            decoded = new FileOutputStream("decoded.txt");
        }
        catch (IOException e) {
            System.err.println("troubles during decoding");
        }
    }
    void parseTree() {
        try {
            int codesNum = encoded.read();
            byte[] bChar = new byte[2];
            int freq;
            HashMap<Character, Integer> frequencies = new HashMap<>();
            for (int i = 0; i < codesNum; i++) {
                encoded.read(bChar);
                freq = encoded.read();
                frequencies.put((char)(((bChar[0]&0x00FF)<<8) + (bChar[1]&0x00FF)), freq);
            }
            Tree tree = new Tree(frequencies);
            HashMap<Character, byte[]> encodeTable = tree.getCharCodes();
            HashMap<Integer, Character> decodeTable = new HashMap<>();
            encodeTable.forEach((Character ch, byte[] b)->{
                int code = 0;
                int size = b.length;
                for(int i = 0; i < size; i++) {
                    code += b[i] << (8*(size - i - 1));
                }
                decodeTable.put(code, ch);
            });
            int textLen = 0;
            textLen = encoded.read();
            for (int i = 0; i < textLen; i++) {

            }
        }
        catch (IOException e) {
            System.err.println("Troubles during tree parsing");
        }
    }
    private Long mod = null;
    private String getChars(Long code) {
        StringBuilder codeSeq = new StringBuilder();
        BareNode curNode = tree.head;
        if (mod != null) {
            int bitNum = Integer.SIZE - Long.numberOfLeadingZeros(mod);
            for (int i = 0; i < bitNum; i++) {
                if ((code >> (bitNum - i - 1) & 1) == 1) {
                    curNode = curNode.right;
                } else {
                    curNode = curNode.left;
                }
            }
        }
        int bitNum = Long.SIZE - Long.numberOfLeadingZeros(code);
        for (int i = 1; i < bitNum; i++) {
            if ((code >> (bitNum - i - 1) & 1) == 1) {
                curNode = curNode.right;
            }
            else {
                curNode = curNode.left;
            }
            if (curNode.ch != null) {
                codeSeq.append(curNode.ch);
                curNode = tree.head;
                mod = (long)(code << i);
                if (mod == 0) {
                    break;
                }
                i++;
            }
        }
        if (curNode == tree.head) {
            mod = null;
        }
        return codeSeq.toString();
    }
    boolean check() {
        parseTree();
        try {
            int curSize = Math.min(Long.BYTES, encoded.available());
            byte buf[] = new byte[Long.BYTES];
            int readNum = encoded.read(buf);
            while (readNum > 0) {
                Long code = ByteBuffer.wrap(buf).order(ByteOrder.BIG_ENDIAN).getLong();
                String curStr = getChars(code);
                System.out.print(curStr);
                curSize = Math.min(Long.BYTES, encoded.available());
                buf = new byte[Long.BYTES];
                readNum = encoded.read(buf);
            }
            encoded.close();
            return true;
        }
        catch (IOException e) {
            System.out.println("Troubles during decoding");
            return false;
        }
    }
}
