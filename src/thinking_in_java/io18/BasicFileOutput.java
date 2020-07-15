package thinking_in_java.io18;

import java.io.*;

public class BasicFileOutput {
    static String file = "src/thinking_in_java/io18/BasicFileOutput.out";
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(
                new StringReader(
                        BufferedInputFile.read("src/thinking_in_java/io18/BasicFileOutput.java")));
//        PrintWriter out = new PrintWriter(
//                new BufferedWriter(new FileWriter(file)));
        PrintWriter out = new PrintWriter(file);
        int lineCount = 1;
        String s;
        while((s = in.readLine()) != null )
            out.println(lineCount++ + ": " + s);
        out.close();
        // Show the stored file:
        System.out.println(BufferedInputFile.read(file));
    }
}
