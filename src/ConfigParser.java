import java.io.*;

public class ConfigParser {
    private int bufSize = 0;
    private FileReader inputFile = null;
    private FileWriter outputFile = null;
    public ConfigParser(String configPath) {
        try {
            FileReader fileReader = new FileReader(configPath);
            BufferedReader bf = new BufferedReader(fileReader);
            String inputPath = null;
            String inParamName = Params.inputFile.getValue();
            String outputPath = null;
            String outParamName = Params.outputFile.getValue();
            Integer bufSize = null;
            String bufParamName = Params.bufSize.getValue();
            boolean succeeded = false;
            while (bf.ready()) {
                String line = bf.readLine();
                line = line.replaceAll("[ \t]", "");
                String[] parts = line.split("=");
                if (parts.length != 2 && !line.isEmpty()) {
                    System.err.println("ERROR: wrong config file format");
                    break;
                }
                if (parts[0].equals(inParamName)) {
                    inputPath = parts[1];
                } else if (parts[0].equals(outParamName)) {
                    outputPath = parts[1];
                } else if (parts[0].equals(bufParamName)) {
                    bufSize = Integer.parseInt(parts[1]);
                }
                if (inputPath != null && outputPath != null && bufSize != null) {
                    if (bufSize > 0) {
                        succeeded = true;
                    }
                    else {
                        System.err.println("ERROR: wrong buffer size");
                    }
                    break;
                }
            }
            if (succeeded) {
                inputFile = new FileReader(inputPath);
                outputFile = new FileWriter(outputPath);
                this.bufSize = 1024 * bufSize;
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
    public FileReader inputFile() {
        return inputFile;
    }
    public FileWriter outputFile() {
        return outputFile;
    }
    public int bufSize() {
        return bufSize;
    }
}
