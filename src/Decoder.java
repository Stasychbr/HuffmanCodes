import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Decoder {
    FileInputStream fos;
    FileReader origin;
    FileInputStream treeFile;
    BareTree tree;
    Decoder(ConfigParser config) {
        try {
            fos = new FileInputStream(config.outputPath());
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
    private String getChars(byte[] arr) {
        String codeSeq = Long.
    }
    boolean check() {
        parseTree();
        try {
            int curSize = Math.min(Long.BYTES, fos.available());
            byte buf[] = new byte[curSize];
            int readNum = fos.read(buf);
            while (readNum > 0) {
                String code =
                curSize = Math.min(Long.BYTES, fos.available());
                buf = new byte[curSize];
                readNum = fos.read(buf);
            }
            fos.close();
            return true;
        }
        catch (IOException e) {
            System.out.println("Troubles during decoding");
            return false;
        }
    }
}
