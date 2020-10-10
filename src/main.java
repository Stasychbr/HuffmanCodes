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
            Manager mg = new Manager(args[0]);
            mg.run();
        }
    }
}