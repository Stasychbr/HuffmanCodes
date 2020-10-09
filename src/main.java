import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;

public class main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("ERROR: Enter the config file name as a program argument");
        }
        else {
            /*ConfigParser cp = new ConfigParser(args[0]);
            HuffmanFactory ht = new HuffmanFactory(cp);
            ht.buildTree();
            HashMap<Character, String> codes = ht.getCodeTable();
            codes.forEach((Character ch, String s)->System.out.println("'" + ch + "': " + s));*/
            Manager mg = new Manager(args[0]);
            mg.run();
            //ht.printCodesTree();
            //ht.parseTree();
            /*try {
                FileOutputStream f = new FileOutputStream("test.txt");
                byte b = Byte.parseByte("001");
                f.write(b);
            }
            catch (IOException e) {
                System.out.println("petux");
            }*/
        }
    }
}