import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("ERROR: Enter the config file name as a program argument");
        }
        else {
            ConfigParser cp = new ConfigParser(args[0]);
            //System.out.println("buffer size: " + cp.bufSize());
            HuffmanFactory ht = new HuffmanFactory(cp);
            ht.buildTree();
            HashMap<Character, String> codes = ht.getCodeTable();
            codes.forEach((Character ch, String s)->System.out.println("'" + ch + "': " + s));
            ht.printCodesTree();
            ht.parseTree();
        }
    }
}