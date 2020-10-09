import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class Reader {
    FileInputStream input;
    int bufSize = 0;
    HuffmanFactory factory;
    Reader(ConfigParser config, HuffmanFactory factory) {
        try {
            input = new FileInputStream(config.inputPath());
        }
        catch (IOException e) {
            System.out.println("Error during reading");
        }
        bufSize = config.bufSize();
        this.factory = factory;
    }
    public void readText() {
        try {
            int curSize = Math.min(bufSize, input.available());
            byte buf[] = new byte[curSize];
            int readNum = input.read(buf);
            while (readNum > 0) {
                factory.countFrequencies(buf);
                curSize = Math.min(bufSize, input.available());
                buf = new byte[curSize];
                readNum = input.read(buf);
            }
            input.close();
        }
        catch (IOException e) {
            System.out.println("Error during reading!");
        }
    }
}
