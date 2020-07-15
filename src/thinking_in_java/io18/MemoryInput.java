package thinking_in_java.io18;

import java.io.*;

public class MemoryInput {
    public static void main(String[] args) throws IOException {
        StringReader in = new StringReader(
                BufferedInputFile.read("src/thinking_in_java/io18/MemoryInput.java"));
        int c;
        while((c = in.read()) != -1)
            System.out.print((char)c);
    }
}
