import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("ERROR: Enter the config file name as a program argument");
        }
        else {
            ConfigParser cp = new ConfigParser(args[0]);
            System.out.println("buffer size: " + cp.bufSize());
            HuffmanTree ht = new HuffmanTree(cp);
            ht.buildTree();
        }
    }
}