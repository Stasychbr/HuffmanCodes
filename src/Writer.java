import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Writer {
    private static final int hexUpCharMask = 0xFF00;
    private static final int hexLowCharMask = 0x00FF;
    FileOutputStream output;
    Writer(FileOutputStream output) {
        this.output = output;
    }
    public void writeTreeMetainfo(HashMap<Character, Integer> frequencies) {
        try {
            output.write(frequencies.size());
            byte[] bChar = new byte[2];
            frequencies.forEach((Character ch, Integer fr)->{
                bChar[0] = (byte)((ch & hexUpCharMask) >> Byte.SIZE);
                bChar[1] = (byte)(ch & hexLowCharMask);
                try {
                    output.write(bChar);
                    output.write(fr);
                }
                catch(IOException e) {
                    Logger.setError(Errors.WriterError);
                }
            });
        }
        catch(IOException e) {
            Logger.setError(Errors.WriterError);
        }
    }
    public void writeEncodedChars(byte[] codes) {
        try {
            output.write(codes.length);
            output.write(codes);
        }
        catch(IOException e) {
            Logger.setError(Errors.WriterError);
        }
    }
}
