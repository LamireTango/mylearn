package thinking_in_java.from1to11;

interface Ex9Interface {
    void say(String s);
}

public class Ex9 {
    Ex9Interface f(final String ss) {
//        class Inner implements Ex9Interface {
//            public void say(String s) {
//                System.out.println(s);
//            }
//        }
        return new Ex9Interface() {
            @Override
            public void say(String s) {
                System.out.println(ss);
            }
        };
    }
    public static void main(String[] args) {
        Ex9 x = new Ex9();
        x.f("hello").say("hello");
    }
}
