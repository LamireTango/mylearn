package thinking_in_java.from1to11;

interface Days {
    int SUNDAY = 1, MONDAY = 2, TUESDAY = 3, WEDNESDAY = 4,
            THURSDAY = 5, FRIDAY = 6, SATURDAY = 7;
}

class Week implements Days {
    private static int count = 0;
    private int id = count++;
    public Week() { System.out.println("Week() " + id); }
}

public class Ex17 {
    public static void main(String[] args) {
        // Without any objects, static fields exist:
        System.out.println("SUNDAY = " + Days.SUNDAY);
        System.out.println("MONDAY = " + Days.MONDAY);
        Week w0 = new Week();
        Week w1 = new Week();
        System.out.println(w1.MONDAY==w1.MONDAY);
        // Error: cannot assign a value to final variable SUNDAY:
        // w.SUNDAY = 2;
        // Error: cannot assign a value to final variable MONDAY:
        // w1.MONDAY = w0.MONDAY;
    }
}
