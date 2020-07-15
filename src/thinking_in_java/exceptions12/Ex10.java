package thinking_in_java.exceptions12;

class Ex10Exception extends Exception{
    void g() throws Exception{
        throw new Ex10Exception();
    }
    void f() throws Exception{
        try {
            g();
        }catch (Ex10Exception e){
            e.printStackTrace(System.out);
            throw new RuntimeException(e);
        }
    }
}

public class Ex10 {
    public static void main(String[] args) {
        try {
            Ex10Exception ex = new Ex10Exception();
            ex.f();
        }catch (Exception e){
            e.printStackTrace(System.out);
        }
    }
}
