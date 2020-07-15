package thinking_in_java.from1to11;

class Person{
    private int age;
    Person(int age){
        this.age=age;
        System.out.println(this.age);}
}

public class Ex15 {
    public Person testPerson(){
        return new Person(10){
            {
                System.out.println("do a test");
            }
        };
    }
    public static void main(String[] args) {
        Ex15 res = new Ex15();
        Person p = res.testPerson();

    }
}
