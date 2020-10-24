import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Writer {
    FileOutputStream output;
    Writer(FileOutputStream output) {
        this.output = output;

    }
    public void writeTreeMetainfo(HashMap<Character, Integer> frequencies) {
        try {
            output.write(frequencies.size());
            byte[] bChar = new byte[2];
            frequencies.forEach((Character ch, Integer fr)->{
                bChar[0] = (byte)((ch & 0xFF00) >> 8);
                bChar[1] = (byte)(ch & 0x00FF);
                try {
                    output.write(bChar);
                    output.write(fr);
                }
                catch(IOException e) {
                    System.err.println("Unable to write codes meta-info in file");
                }
            });
        }
        catch(IOException e) {
            System.err.println("Unable to write codes meta-info in file");
        }
    }
    public void writeEncodedChars(byte[] codes) {
        try {
            output.write(codes.length);
            output.write(codes);
        }
        catch(IOException e) {
            System.err.println("Unable to write encoded info in file");
        }
    }
}
