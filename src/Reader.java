import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class Reader {
    final FileInputStream input;
    final int bufSize;
    final HuffmanFactory factory;
    Reader(FileInputStream fis, int bufSize, HuffmanFactory factory) {
        input = fis;
        this.bufSize = bufSize;
        this.factory = factory;
    }
    public void run() {
        try {
            int curSize = Math.min(bufSize, input.available());
            byte[] buf = new byte[curSize];
            int readNum = input.read(buf);
            while (readNum > 0) {
                factory.run(buf);
                curSize = Math.min(bufSize, input.available());
                if (curSize != buf.length) {
                    buf = new byte[curSize];
                }
                readNum = input.read(buf);
            }
        }
        catch (IOException e) {
            System.err.println("Error during reading!");
        }
    }
}
