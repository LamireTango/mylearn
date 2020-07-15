package thinking_in_java.exceptions12;

class A extends Exception{
    void f()throws A{throw new A();}
}
class B extends A{
    void f()throws B{throw new B();}
}
class C extends B{
    void f()throws C{throw new C();}
}

public class Ex25 {
    public static void main(String[] args) {
        A ex = new C();
        try {
            ex.f();
        }catch (C e){
            System.out.println("C catch");
            e.printStackTrace();
        }catch (A e){
            System.out.println("A catch");
            e.printStackTrace();
        }
    }
}
