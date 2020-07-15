package thinking_in_java.arrays16;

import java.util.*;
public class JavaUtilArrays {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        int[] ints =new int[10];
        int[] ints1;
        //Arrays类的一些用法
        Arrays.fill(ints,10);
        System.out.println(Arrays.toString(ints));

        Arrays.fill(ints,0,ints.length,20);
        System.out.println(Arrays.toString(ints));

        ints1 = Arrays.copyOf(ints,15);
        System.out.println(Arrays.toString(ints1));

        ints1 = Arrays.copyOfRange(ints,5,20);
        System.out.println(Arrays.toString(ints1));

    }


}
