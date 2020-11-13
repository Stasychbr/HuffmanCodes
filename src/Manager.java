import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Manager {
    ConfigParser config;
    Manager(String cfgPath) {
        config = new ConfigParser(cfgPath);
        Logger.printError();
    }
    void run() {
        try {
            if (config.mode() == Mode.Encode) {
                FileInputStream fis = new FileInputStream(config.inputPath());
                FileOutputStream fos = new FileOutputStream(config.outputPath());
                Writer writer = new Writer(fos);
                HuffmanFactory factory = new HuffmanFactory(writer);
                Reader reader = new Reader(fis, config.bufSize(), factory);
                reader.run();
                if (Logger.isError()) {
                    Logger.printError();
                    return;
                }
                fis.close();
                fos.close();
            }
            else {
                Decoder decoder = new Decoder(config);
                if (Logger.isError()) {
                    Logger.printError();
                    return;
                }
                decoder.run();
            }
        }
        catch (IOException e) {
            Logger.setError(Errors.FilesError);
            Logger.printError();
        }
    }
}
