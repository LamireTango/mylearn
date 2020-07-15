package thinking_in_java.generics15;

class Pair<T>{
    private T first;
    private T last;
    public Pair(T first,T last){
        this.first = first;
        this.last = last;
    }
    public Pair(){}
    public T getFirst() {
        return first;
    }
    public void setFirst(T first) {
        this.first = first;
    }
    public T getLast() {
        return last;
    }
    public void setLast(T last) {
        this.last = last;
    }
}
class IntPair extends Pair<Integer>{
    public IntPair(Integer first, Integer last) {
        super(first, last);
    }
}


public class Test {
    public static double Add(Pair<? extends Number> p){
        Number first = p.getFirst();
        Number last = p.getLast();
//        p.setFirst(first);//error,无法调用传入Number引用的方法
        return first.doubleValue()+last.doubleValue();
    }
    public static void Add2(Pair<? super Number> p){
        Object first = p.getFirst();//error
        p.setFirst(1);
        p.setLast(1.1);
    }
    public static <T> T createObject(Class<T> clz) throws Exception{
        T t = clz.newInstance();
        return t;
    }
    public static void main(String[] args) throws Exception{
        Pair<String> pair1 = new Pair<>("one","two");
        Pair<Integer> pair2 = new Pair<>(1,2);
        System.out.println(pair1.getClass());//class Pair
        System.out.println(pair1.getClass()==Pair.class);//true
        System.out.println(pair1.getClass()==pair2.getClass());//true


    }
}
