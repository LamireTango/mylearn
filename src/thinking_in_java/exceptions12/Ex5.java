package thinking_in_java.exceptions12;

public class Ex5 {
    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5};
        int i = 10;
        while (true){
            try {
                System.out.println(i+" : "+nums[i]);
                break;
            }catch (ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
                System.out.println(e.getMessage());
                i--;
            }
        }
    }
}
