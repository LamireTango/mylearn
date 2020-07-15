package thinking_in_java.generics15;



//import thinking_in_java.from1to11.Customer;

import java.util.Collection;
import java.util.*;

class BasicGenerator<T> implements Generator<T> {
    private Class<T> type;
    public BasicGenerator(Class<T> type){ this.type = type; }
    public T next() {
        try {
            // Assumes type is a public class:
            return type.newInstance();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    // Produce a Default generator given a type token:
    public static <T> Generator<T> create(Class<T> type) {
        return new BasicGenerator<T>(type);
    }
}

public class Ex13 {
}
class Generators {
    public static <T> List<T>
    fill(List<T> coll, Generator<T> gen, int n) {
        for(int i = 0; i < n; i++)
            coll.add(gen.next());
        return coll;
    }
    public static void main(String[] args) {
        List<Integer> list = fill(new ArrayList<>(),new CountingGenerator(),10);
        System.out.println(list);
        List<Integer> list2 = fill(new ArrayList<>(),new CountingGenerator(),10);
        System.out.println(list2);
    }

    private static class CountingGenerator implements Generator{
        private static int count = 0;
//        private final int n = count++;
        @Override
        public Integer next() {
            return count++;
        }
    }
}