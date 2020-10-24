import java.io.*;

public class ConfigParser {
    private Integer bufSize = null;
    private String inputPath = null;
    private String outputPath = null;
    public ConfigParser(String configPath) {
        try {
            FileReader fileReader = new FileReader(configPath);
            BufferedReader bf = new BufferedReader(fileReader);
            while (bf.ready()) {
                String line = bf.readLine();
                line = line.replaceAll("[ \t]", "");
                String[] parts = line.split("=");
                if (parts.length != 2 && !line.isEmpty()) {
                    System.err.println("ERROR: wrong config file format");
                    break;
                }
                if (parts[0].equals(Params.inputFile.getValue())) {
                    inputPath = parts[1];
                } else if (parts[0].equals(Params.outputFile.getValue())) {
                    outputPath = parts[1];
                } else if (parts[0].equals(Params.bufSize.getValue())) {
                    bufSize = 1024 * Integer.parseInt(parts[1]);
                }
                if (inputPath != null && outputPath != null && bufSize != null) {
                    if (bufSize <= 0) {
                        System.err.println("ERROR: wrong buffer size");
                    }
                    break;
                }
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
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
}
