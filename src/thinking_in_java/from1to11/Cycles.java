package thinking_in_java.from1to11;
interface Cycle {
    void ride();
}

interface CycleFactory {
    Cycle getCycle();
}

class Unicycle implements Cycle {
    public void ride() {
        System.out.println("Ride Unicycle"); }
    public static CycleFactory cf = new CycleFactory() {
        @Override
        public Unicycle getCycle() {
            return new Unicycle();
        }
    };
}


public class Cycles {
    public static void rideCycle(CycleFactory factory) {
        Cycle c = factory.getCycle();
        c.ride();
    }
    public static void main(String [] args) {
        CycleFactory f = Unicycle.cf;
//        rideCycle(new BicycleFactory());
//        rideCycle(new TricycleFactory());
    }
}
