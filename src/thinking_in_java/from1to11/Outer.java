package thinking_in_java.from1to11;

interface Cooking{

}

class Outer {
    private int oi = 1;
    private void hi() { System.out.println("Outer hi"); }
    class Inner {
    }
    public Inner modifyOuter(){
        return new Inner(){
            {
            oi *= 2;
            hi();}
            private int ii;
        };
    }
    public void showOi() { System.out.println(oi); }
//    void testInner() {
//        Inner in = new Inner();
//        in.modifyOuter();
//    }
    public static void main(String[] args) {
        Outer out = new Outer();
        out.showOi();
        out.modifyOuter();
        out.showOi();
    }
}
