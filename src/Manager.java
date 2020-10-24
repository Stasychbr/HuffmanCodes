import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Manager {
    ConfigParser config;
    Manager(String cfgPath) {
        config = new ConfigParser(cfgPath);
    }
    void run() {
        try {
            FileInputStream fis = new FileInputStream(config.inputPath());
            FileOutputStream fos = new FileOutputStream(config.outputPath());
            Writer writer = new Writer(fos);
            HuffmanFactory factory = new HuffmanFactory(writer);
            Reader reader = new Reader(fis, config.bufSize(), factory);
            reader.run();
        }
        catch (IOException e) {
            System.err.println("Unable to open input or output file");
        }
        //Decoder checker = new Decoder(config);
        //checker.check();
    }
}
