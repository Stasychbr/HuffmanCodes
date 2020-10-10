import java.util.HashMap;

public class Manager {
    ConfigParser config = null;
    Manager(String cfgPath) {
        config = new ConfigParser(cfgPath);
    }
    void run() {
        HuffmanFactory factory = new HuffmanFactory(config);
        Reader r = new Reader(config, factory);
        r.readText();
        factory.buildTree();
        // dbg info
        HashMap<Character, Integer> ct = factory.getCodeTable();
        ct.forEach((character, integer) -> System.out.println(character + ": " + Integer.toBinaryString(integer)));
        //dbg info
        factory.printCodesTree();
        factory.encodeText();
        Decoder checker = new Decoder(config);
        checker.check();
    }
}
