package thinking_in_java.exceptions12;
import java.lang.Exception;

class Person{
    void say(){
        System.out.println("Person can say.");
    }
}
class myException extends Exception{
    String s;
    myException(String s){this.s = s;}
    void showS(){
        System.out.println(s);
    }
    public void f() throws myException{throw new myException("这是s字符串");}
}
public class Ex1 {
    public static void main(String[] args) throws Exception{
        //Ex1
        try {
            throw new Exception("字符串");
        }catch (Exception e){
            e.printStackTrace(System.out);
        }finally {
            System.out.println("一定会执行");
        }
        //Ex2
        Person p=null;
        try {
            p.say();
        }catch (Exception e){
            e.printStackTrace(System.out);
        }
        //Ex3
        int[] nums = {1,2,3,4,5};
        try {
            for(int i = 0;i<=nums.length;i++)
                System.out.print(nums[i]);
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        //Ex4
        myException mye = new myException("my in main");
        try {
            throw mye;
        }catch (myException e){
            e.showS();
            e.printStackTrace(System.out);
            throw e;
        }
    }
}
