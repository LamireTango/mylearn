package thinking_in_java.strings13;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InfiniteRecursion {
    public String toString() {
        return " InfiniteRecursion address: " + super.toString() + "\n";
    }
    public static void main(String[] args) {
        List<InfiniteRecursion> v =
                new ArrayList<InfiniteRecursion>();
        for(int i = 0; i < 10; i++)
            v.add(new InfiniteRecursion());
        System.out.println(v);
        int i = 1;
        System.out.println("-----------------------------------");
        String s = "asd asdfasd  dadfa";
        String[] ss = (s.split(" "));



    }
}
