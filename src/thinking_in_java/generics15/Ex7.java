package thinking_in_java.generics15;

import java.util.Iterator;




public class Ex7 implements Generator<Integer>, Iterable<Integer> {
    private int count = 0;
    private int n;
    public Ex7(int i){n = i;}
    public Integer next() { return fib(count++); }
    private int fib(int n) {
        if(n < 2) return 1;
        return fib(n-2) + fib(n-1);
    }
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return n>0;
            }
            @Override
            public Integer next() {
                n--;
                return Ex7.this.next();
            }
        };
    }
    public static void main(String[] args) {
        for(int i:new Ex7(10))
            System.out.print(i+" ");
    }
}
