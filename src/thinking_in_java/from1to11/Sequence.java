package thinking_in_java.from1to11;

import java.util.*;

interface Selector{
    boolean end();
    Object current();
    void next();
}

public class Sequence {
    private ArrayList<Integer> items;
    private int next = 0;
    public Sequence() { items = new ArrayList<>(); }
    public void add(int x) {
        items.add(x);
    }
    private class SequenceSelector implements Selector {
        private int i = 0;
        public boolean end() { return i == items.size(); }
        public Object current() { return items.get(i); }
        public void next() { if(i < items.size()) i++; }
    }
    public Selector selector() {
        return new SequenceSelector();
    }
    public static void main(String[] args) {
        Sequence sequence = new Sequence();
        for(int i = 0; i < 10; i++)
            sequence.add(i);
        Selector selector = sequence.selector();
        while(!selector.end()) {
            System.out.print(selector.current() + " ");
            selector.next();
        }
        int[] nums = {1,3,7,8,9};
        ArrayList<Integer> list = new ArrayList<>();
//        System.out.println(Arrays.asList(1,3,5));
        String s = "+100";
        char[] c = s.trim().toCharArray();
    }
}
