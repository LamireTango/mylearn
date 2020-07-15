package thinking_in_java.typeInformation14;

import java.util.Arrays;

class A{
    private int a;
}
class B extends A{
    public String b;
    public A ba;
}
class C extends B{

}

public class Ex8 {
    public static void printInfo(Class obj) {
        System.out.println(obj.getName());
        System.out.println(Arrays.toString(obj.getDeclaredFields()));
        if(obj.getSuperclass()!=null)
            printInfo(obj.getSuperclass());
    }
    public static void main(String[] args) throws Exception{
        TypeCounter tc = new TypeCounter(A.class);
        tc.count(new C());
        System.out.println(tc);
        tc.count(new B());
        System.out.println(tc);
        tc.count(new A());
        System.out.println(tc);
        System.out.println(0xA<<2);
    }
}
