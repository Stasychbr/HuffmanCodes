import com.sun.xml.internal.ws.util.ByteArrayBuffer;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

public class Decoder {
    private static final int hexUpCharMask = 0xFF00;
    private static final int hexLowCharMask = 0x00FF;
    FileInputStream encoded;
    FileWriter decoded;
    Decoder(ConfigParser config) {
        try {
            encoded = new FileInputStream(config.inputPath());
            decoded = new FileWriter(config.outputPath());
        }
        catch (IOException e) {
            Logger.setError(Errors.DecoderError);
        }
    }
    void run() {
        try {
            int codesNum = encoded.read();
            byte[] bChar = new byte[Character.BYTES];
            int freq;
            HashMap<Character, Integer> frequencies = new HashMap<>();
            for (int i = 0; i < codesNum; i++) {
                encoded.read(bChar);
                freq = encoded.read();
                frequencies.put((char)((bChar[0] << Byte.SIZE) + bChar[1]), freq);
            }
            Tree tree = new Tree(frequencies);
            HashMap<Character, byte[]> encodeTable = tree.getCharCodes();
            HashMap<Integer, Character> decodeTable = new HashMap<>();
            encodeTable.forEach((Character ch, byte[] b)->{
                int code = 0;
                int size = b.length;
                for(int i = 0; i < size; i++) {
                    code += b[i] << (Byte.SIZE * (size - i - 1));
                }
                decodeTable.put(code, ch);
                //System.out.println(ch+" " + code); /dbg
            });
            int textLen = 0;
            textLen = encoded.read();
            byte[] encodedText = new byte[textLen];
            encoded.read(encodedText);
            StringBuilder decodedText = new StringBuilder();
            for (int i = 0; i < textLen;) {
                int curCode = Integer.MIN_VALUE + 1;
                char letter = decodeTable.getOrDefault(curCode, (char)0);
                curCode = 0;
                while(letter == 0) {
                    curCode <<= Byte.SIZE;
                    curCode += encodedText[i++];
                    letter = decodeTable.getOrDefault(curCode, (char)0);
                }
                decodedText.append(letter);
            }
            decoded.write(decodedText.toString());
            decoded.close();
        }
        catch (IOException e) {
            Logger.setError(Errors.DecoderError);
        }
    }
}
