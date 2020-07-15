package mycode;
import java.io.*;

import java.lang.reflect.*;
import java.math.BigInteger;
import java.nio.Buffer;
import java.sql.Array;
import java.util.*;
/**
 * Shift+Enter 跳行
 * Ctrl+D 复制该行
 * Ctrl+Y 删除该行
 * Ctrl+Shift+↑ 互换位置
 */
import mycode.Solution;
 class ListNode {
     int val;
     ListNode next;
     ListNode(int x) { val = x; }
 }

public class Main {

    public static void main(String[] args) throws Exception {
        Solution ss = new Solution();
        TreeMap<Integer,String> treeMap = new TreeMap<>();
        treeMap.put(1,"1");
        treeMap.put(4,"1111");
        treeMap.put(2,"11");
        treeMap.put(3,"111");
        Integer[] i = treeMap.keySet().toArray(new Integer[0]);
        int index = Arrays.binarySearch(i,0);
        System.out.println(index);
        System.out.println(Arrays.toString(i));


    }
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1==null) return l2;
        if(l2==null) return l1;
        ListNode res = l1.val>l2.val?l2:l1;
        ListNode temp = res;
        if(l1.val>l2.val)
            l2=l2.next;
        else
            l1=l1.next;
        while (l1!=null&&l2!=null){
            if(l1.val>l2.val){
                temp.next = l2;
                temp = temp.next;
                l2=l2.next;
            }
            else{
                temp.next = l1;
                temp = temp.next;
                l1=l1.next;
            }
        }
        if(l1!=null)
            temp.next = l1;
        if(l2!=null)
            temp.next = l2;
        return res;
    }

    //删除表中的偶数
    public static void removeEvens(List<Integer> lst) {
        //迭代器的基本法则：
        // 如果对正在被迭代的集合进行结构上的改变（对集合使用add、remove、clear），迭代器将不再合法
        Iterator<Integer> itr = lst.iterator();
        ListIterator<Integer> litr = lst.listIterator();//iterator的扩展，略
        while (itr.hasNext()) {
            if (itr.next() % 2 == 0)//第一次调用.next，迭代器给出的是第一项
                itr.remove();//不能再次使用remove，除非先调用next
        }
    }
    public static int getA(int i){
        return Integer.parseInt(String.valueOf(i).substring(0,2));
    }
}