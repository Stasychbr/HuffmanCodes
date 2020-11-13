import com.sun.xml.internal.ws.util.ByteArrayBuffer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
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
            output.write(ByteBuffer.allocate(Integer.BYTES).putInt(frequencies.size()).array());
            System.out.println("codesNum " + frequencies.size()); //dbg
            byte[] bChar = new byte[2];
            frequencies.forEach((Character ch, Integer fr)->{
                bChar[0] = (byte)((ch & hexUpCharMask) >> Byte.SIZE);
                bChar[1] = (byte)(ch & hexLowCharMask);
                try {
                    output.write(bChar);
                    output.write(ByteBuffer.allocate(Integer.BYTES).putInt(fr).array());
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
            output.write(ByteBuffer.allocate(Integer.BYTES).putInt(codes.length).array());
            System.out.println("textLen " + codes.length);
            output.write(codes);
        }
        catch(IOException e) {
            Logger.setError(Errors.WriterError);
        }
    }
}
