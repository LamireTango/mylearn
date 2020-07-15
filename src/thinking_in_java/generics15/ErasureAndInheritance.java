package thinking_in_java.generics15;

class GenericBase<T> {
    private T element;
    public void set(T arg) {
        if(arg instanceof Integer)
            System.out.println(1);
        element = arg;
        System.out.println(arg);}
    public T get() { return element; }
}

class Derived1<T> extends GenericBase<T> {}

class Derived2 extends GenericBase {} // No warning

// class Derived3 extends GenericBase<?> {}
// Strange error:
//   unexpected type found : ?
//   required: class or interface without bounds

public class ErasureAndInheritance {
    public static void main(String[] args) {
        Derived2 d2 = new Derived2();
        Object obj = d2.get();
        d2.set(obj); // Warning here!
    }
}
