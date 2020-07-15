package thinking_in_java.from1to11;

import java.nio.CharBuffer;
import java.util.Random;
import java.util.Scanner;

public class RandomChar {
    private static Random ra = new Random(18);
    public char next() {return c[ra.nextInt(26)];}
    private char[] c = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public static void main(String[] args){
        Scanner s = new Scanner(new AdaptedRandomChar(10));
        while (s.hasNext())
            System.out.print(s.next());
    }
}

class AdaptedRandomChar implements Readable{
    RandomChar randomchar = new RandomChar();
    private int count;
    public AdaptedRandomChar(int count){
        this.count = count;
    }
    @Override
    public int read(CharBuffer cb){
        if(count-- == 0) return -1;
        String result = Character.toString(randomchar.next());
        cb.append(result);
        return result.length();
    }
}
