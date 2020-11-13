import com.sun.xml.internal.ws.util.ByteArrayBuffer;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

public class Decoder {
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
            while (encoded.available() > 0) {
                byte[] IntByteBuf = new byte[Integer.BYTES];
                encoded.read(IntByteBuf);
                int codesNum = ByteBuffer.wrap(IntByteBuf).getInt();
                System.out.println("Codes Num: " + codesNum);
                byte[] bChar = new byte[Character.BYTES];
                HashMap<Character, Integer> frequencies = new HashMap<>();
                for (int i = 0; i < codesNum; i++) {
                    encoded.read(bChar);
                    encoded.read(IntByteBuf);
                    frequencies.put(ByteBuffer.wrap(bChar).getChar(), ByteBuffer.wrap(IntByteBuf).getInt());
                }
                Tree tree = new Tree(frequencies);
                HashMap<Character, byte[]> encodeTable = tree.getCharCodes();
                HashMap<Integer, Character> decodeTable = new HashMap<>();
                encodeTable.forEach((Character ch, byte[] b) -> {
                    int code = 0;
                    int size = b.length;
                    for (int i = 0; i < size; i++) {
                        code += b[i] << (Byte.SIZE * (size - i - 1));
                    }
                    decodeTable.put(code, ch);
                    //System.out.println(ch+" " + code); /dbg
                });
                encoded.read(IntByteBuf);
                int textLen = ByteBuffer.wrap(IntByteBuf).getInt();
                System.out.println("textLen " + textLen); //dbg
                byte[] encodedText = new byte[textLen];
                encoded.read(encodedText);
                StringBuilder decodedText = new StringBuilder();
                char letter;
                int curCode;
                for (int i = 0; i < textLen; ) {
                    curCode = 0;
                    letter = 0;
                    while (letter == 0) {
                        curCode <<= Byte.SIZE;
                        curCode += encodedText[i++];
                        letter = decodeTable.getOrDefault(curCode, (char) 0);
                    }
                    decodedText.append(letter);
                }
                decoded.write(decodedText.toString());
            }
            decoded.close();
        }
        catch (IOException e) {
            Logger.setError(Errors.DecoderError);
        }
    }
}
