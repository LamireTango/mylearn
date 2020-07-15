package thinking_in_java.exceptions12;

public class NeverCaught {
    static void f(){
        throw new RuntimeException("From f()");
    }
    static void g() {
        f();
    }
    public static void main(String[] args) {
        try {
            g();
        }catch (Exception e){
            e.printStackTrace(System.out);
        }
    }
}
