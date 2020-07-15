package thinking_in_java.typeInformation14;

import java.util.HashMap;
import java.util.Map;
/*实现类的计数器*/

public class TypeCounter extends HashMap<Class<?>,Integer> {
    private Class<?> baseType;
    public TypeCounter(Class<?> baseType){
        this.baseType = baseType;
    }
    public void count(Object obj){
        Class<?> type = obj.getClass();
        //判断baseType是否与type同一个类或为其父类
        if(!baseType.isAssignableFrom(type)){
            throw new RuntimeException(obj + " 是错误的类型: " + type + "; 应是类："+baseType+"或其子类 ");
        }
        countClass(type);//实现计数
    }
    private void countClass(Class<?> type){
        Integer quantity = get(type);//HashMap.get()
        put(type,quantity==null?1:quantity+1);
        //向上递归计数
        Class<?> superClass = type.getSuperclass();
        if(superClass != null && baseType.isAssignableFrom(superClass))
            countClass(superClass);
    }
    public String toString(){
        StringBuilder res = new StringBuilder("{");
        for(Map.Entry<Class<?>,Integer> pair : entrySet()){
            res.append(pair.getKey().getSimpleName());
            res.append("=");
            res.append(pair.getValue());
            res.append(", ");
        }
        res.delete(res.length()-2,res.length());
        res.append("}");
        return res.toString();
    }
}
