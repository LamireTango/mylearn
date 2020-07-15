package thinking_in_java.typeInformation14;
class Gum{}
public class Test {
    public static void main(String[] args) throws Exception{
//        Gum g = new Gum();
        try {
            Class.forName("thinking_in_java.typeInformation14.Gum");
        }catch (ClassNotFoundException e){
            System.out.println("not found");
        }

    }
}
