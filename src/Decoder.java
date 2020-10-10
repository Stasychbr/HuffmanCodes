import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Decoder {
    FileInputStream encoded;
    FileReader origin;
    FileInputStream treeFile;
    BareTree tree;
    Decoder(ConfigParser config) {
        try {
            encoded = new FileInputStream(config.outputPath());
            origin = new FileReader(config.inputPath());
            treeFile = new FileInputStream(config.treePath());
        }
        catch (IOException e) {
            System.out.println("troubles during decoding");
        }
    }
    boolean parseTree() {
        try {
            ObjectInputStream oin = new ObjectInputStream(treeFile);
            tree = (BareTree) oin.readObject();
            System.out.println("parsed");
            return true;
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("parsing went wrong");
            return false;
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
