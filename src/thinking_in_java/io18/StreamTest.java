package thinking_in_java.io18;

import java.io.*;

public class StreamTest {
    public static void main(String[] args) throws Exception{
        System.out.println("start");
        //Scanner in = new Scanner(System.in);//读取数据
        FileStreamTest3();
//        System.out.println(in);
    }
    public static void SystemInTest() throws Exception{
        byte[] buffer = new byte[1024];
        int len = System.in.read(buffer);
        String s = new String(buffer,0,len);
        System.out.println("read " + len +" byte");
        System.out.println(s);
        System.out.println("s.length:"+s.length());
    }
    public static void FileStreamTest() throws Exception{
        byte[] buf = new byte[10];
        for(int i=0;i<buf.length;i++){
            buf[i] = (byte)i;
        }
        FileOutputStream out = new FileOutputStream("FileStreamTest.txt");
        out.write(buf);
        out.close();
    }
    public static void FileStreamTest2() throws Exception{
        PrintWriter out = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream("FileStreamTest.txt"))));
        out.println("100");
        out.close();
    }
    public static void FileStreamTest3() throws Exception{
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("src/thinking_in_java/io18/test.txt"),"utf8"));
        String line;
        while((line=in.readLine())!=null)
            System.out.println(line);
    }
}
