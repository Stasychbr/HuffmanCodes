import java.io.*;

public class ConfigParser {
    private Integer bufSize = null;
    private String inputPath = null;
    private String outputPath = null;
    private Mode mode = null;
    public ConfigParser(String configPath) {
        try {
            boolean succeed = false;
            FileReader fileReader = new FileReader(configPath);
            BufferedReader bf = new BufferedReader(fileReader);
            while (bf.ready()) {
                String line = bf.readLine();
                line = line.replaceAll("[ \t]", "");
                String[] parts = line.split(Params.delimiter.getValue());
                if (parts.length != 2 && !line.isEmpty()) {
                    Logger.setError(Errors.CfgFormat);
                    break;
                }
                if (parts[0].equals(Params.inputFile.getValue())) {
                    inputPath = parts[1];
                } else if (parts[0].equals(Params.outputFile.getValue())) {
                    outputPath = parts[1];
                } else if (parts[0].equals(Params.bufSize.getValue())) {
                    bufSize = 1024 * Integer.parseInt(parts[1]);
                }
                else if (parts[0].equals(Params.mode.getValue())) {
                    if (parts[1].equals(Mode.Encode.getValue())) {
                        mode = Mode.Encode;
                    }
                    else if (parts[1].equals(Mode.Decode.getValue())) {
                        mode = Mode.Decode;
                    }
                }
                if (inputPath != null && outputPath != null && bufSize != null && mode != null) {
                    if (bufSize <= 0) {
                        Logger.setError(Errors.CfgFormat);
                    }
                    succeed = true;
                    break;
                }
            }
            if (!succeed) {
                Logger.setError(Errors.CfgFormat);
            }
        }
        catch (IOException e) {
            Logger.setError(Errors.CfgNotFound);
        }
    }
    public String inputPath() {
        return inputPath;
    }
    public String outputPath() {
        return outputPath;
    }
    public int bufSize() {
        return bufSize;
    }
    public Mode mode() {
        return mode;
    }
}
