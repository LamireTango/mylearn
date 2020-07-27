package mycode;
//import com.sun.javafx.collections.MappingChange;
//import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import com.sun.source.tree.Tree;

import java.util.*;
import java.util.Map.Entry;


class TestSolution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] pushed = new int[]{7, 6, 5, 4};
        int[] popped = new int[]{4, 3, 5, 2, 1, 10};
        int[][] events = {{2, 3}, {2, 3}, {1, 5}, {1, 5}, {1, 5}};
        solution.generateTrees(3);
    }
}
public class Solution {
    protected int i;
    //求最大子序列和
    public int maxSubArray(int[] nums) {
        int temp = 0;
        int res = 0;
        for(int i=0;i<nums.length;i++){
            temp += nums[i];
            if(temp<=0)
                temp = 0;
            else if(res<=temp)
                res = temp;
        }
        Arrays.sort(nums);
        return nums[nums.length-1]>0? res : nums[nums.length-1];
    }
    //LeetCode179
    public String largestNumber(int[] nums) {
        String[] str = new String[nums.length];
        for(int i=0;i<nums.length;i++){
            str[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(str, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String a = o1 +"" +o2;
                String b = o2+ "" +o1;
                return b.compareTo(a);
            }
        });
        if(str[0]=="0")
            return "0";
        String res = "";
        for(String i:str)
            res += i;
        return res;
    }
    //构造“ab+cde+**”的表达式树
    public static void build_tree(){
        char[] s={'a','b','+','c','d','e','+','*','*'};
        Stack<TreeNode> sta = new Stack<TreeNode>();
        for(char i:s){
            if(i=='+' || i=='*'){
                TreeNode root = new TreeNode(i);
                TreeNode right = sta.pop();
                TreeNode left = sta.pop();
                root.right = right;
                root.left = left;
                sta.push(root);
            }else {
                sta.push(new TreeNode(i));
            }
        }
        traversal(sta.peek());
    }
    public static void traversal(TreeNode root){
        //inorder
        if(root != null){
            if(root.left!=null && root.right!=null)
                System.out.print('(');
            traversal(root.left);
            System.out.print(root.val);
            traversal(root.right);
            if(root.left!=null && root.right!=null)
                System.out.print(')');
        }
    }
    //用map.entrySet遍历map的key和value
    public static void travelmap(){
        Map<Integer,Integer> map = new TreeMap<>();
        map.put(1,1);
        map.put(2,2);
        map.put(3,4);
        Set<Map.Entry<Integer,Integer>> set = map.entrySet();
        //方法1，迭代器
        Iterator<Map.Entry<Integer,Integer>> it = set.iterator();
        while(it.hasNext()){
            Map.Entry<Integer,Integer> thisit = it.next();
            System.out.println("key is " + thisit.getKey() + "value is " + thisit.getValue());
        }
        //方法2，增强for循环
        for(Map.Entry<Integer,Integer> temp: set){
            System.out.println("key is " + temp.getKey() + "value is " + temp.getValue());
        }
    }
    //归并排序
    public static void mergesort(int[] nums){
        mergesort(nums,0,nums.length-1);

    }
    private static void mergesort(int[] nums,int left,int right){
        if(right>left){
            int mid = (left+right)/2;
            mergesort(nums, left, mid);
            mergesort(nums, mid+1, right);
            //归并已排序数组
            int[] tempnums = new int[right-left+1];
            int l1 = left;
            int l2 = mid+1;
            int i = 0;
            for(;i<tempnums.length;i++){
                if(nums[l1]>nums[l2])
                    tempnums[i] = nums[l2++];
                else
                    tempnums[i] = nums[l1++];
                if(l1>mid || l2>right){
                    i++;
                    break;
                }
            }
            if(l1>mid)
                for(;i<tempnums.length;i++)
                    tempnums[i] = nums[l2++];
            else if(l2>right)
                for(;i<tempnums.length;i++)
                    tempnums[i] = nums[l1++];
            //赋值
            System.out.println(Arrays.toString(tempnums));
            for(i=0;i<tempnums.length;i++)
                nums[left++] = tempnums[i];
//            System.out.println(Arrays.toString(nums));
        }
    }
    //基数排序（桶排序）
    public static void radixsort(int[] nums){
        int maxnum = 0;
        for(int i:nums)
            if(maxnum<i)
                maxnum = i;
        int radix_num = (int)Math.log10(maxnum) + 1;
        ArrayList<Integer>[] bucket = new ArrayList[10];//十个桶，代表0-9
        for(int i=0;i<radix_num;i++){
            for(int j=0;j<10;j++){
                bucket[j] = new ArrayList<>();
            }
            //按个位/十位/百位..排序（入桶）
            for(int j=0;j<nums.length;j++){
                int temp = nums[j] % (int)Math.pow(10,i+1) / (int)Math.pow(10,i);//i%10/1,i%100/10,,,
                bucket[temp].add(nums[j]);
            }
            //排序完后放入nums数组，进行下一步排序
            int index = 0;
            for(ArrayList<Integer> ibucket:bucket){
                for(int sortnum:ibucket){
                    nums[index++] = sortnum;
                }
            }
            System.out.println(Arrays.toString(nums));
        }
    }
    //查并集 LeetCode684
    public int[] findRedundantConnection(int[][] edges) {
        int[] parent = new int[edges.length+1];
        Arrays.fill(parent,-1);
        for(int i=0;i<edges.length;i++){
            int s1 = dsufind(parent,edges[i][0]);
            int s2 = dsufind(parent,edges[i][1]);
            if (s1 == s2){
                return edges[i];
            }else {
                dsuunion(parent,s1,s2);
            }
        }
        return new int[]{0,0};
    }
    private void dsuunion(int[] parent,int root1,int root2){
//        parent[root2] = root1;
        //按秩合并
        if(parent[root1]<parent[root2]){
            parent[root2] = root1;
        }else{
            if(parent[root2] == parent[root1])
                parent[root2]--;
            parent[root1] = root2;
        }
    }
    private int dsufind(int[] parent,int i){
        if(parent[i]<0)
            return i;
        else
//            return dsufind(parent,parent[i]);
            return parent[i]=dsufind(parent,parent[i]); //路径压缩
    }
    //回溯算法 LeetCode131
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        List<String> list = new ArrayList<>();
        partitionhelp(res,list,s,0);
        return res;
    }
    public void partitionhelp(List<List<String>> res,List<String> list,String s,int start){
        if(start == s.length()){
            res.add(new ArrayList<>(list));
            return;
        }
        for(int i=start+1;i<=s.length();i++){
            String temp = s.substring(start,i);
            String retemp = new StringBuilder(temp).reverse().toString();
            if(temp.equals(retemp)){
                list.add(temp);
                partitionhelp(res,list,s,i);
                list.remove(list.size()-1);
            }
        }
    }
    //输入某二叉树的前序遍历和中序遍历的结果，请重建该二叉树 LeetCode105
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder.length==0)
            return null;
        TreeNode head = new TreeNode(preorder[0]);
        buildTreeHelp(head,preorder,inorder);
        return head;
    }
    private void buildTreeHelp(TreeNode x,int[] preorder,int[] inorder){
        int xvalue = x.val;
        int i=0;
        for(;i<=inorder.length;i++)
            if(inorder[i]==xvalue)
                break;
        int[] l_inorder = new int[i];
        int[] r_inorder = new int[inorder.length-1-i];
        //src：源数组;srcPos：源数组要复制的起始位置;dest：目标数组;destPos：目标数组复制的起始位置;length：复制的长度
        System.arraycopy(inorder, 0, l_inorder, 0, l_inorder.length);
        System.arraycopy(inorder, i + 1, r_inorder, 0, r_inorder.length);
        int[] l_preorder = new int[l_inorder.length];
        int[] r_preorder = new int[r_inorder.length];
        System.arraycopy(preorder,1,l_preorder,0,l_preorder.length);
        System.arraycopy(preorder,1+l_preorder.length,r_preorder,0,r_preorder.length);
        if(l_preorder.length!=0){
            x.left = new TreeNode(l_preorder[0]);
            buildTreeHelp(x.left,l_preorder,l_inorder);
        }
        if(r_preorder.length!=0){
            x.right = new TreeNode(r_preorder[0]);
            buildTreeHelp(x.right,r_preorder,r_inorder);
        }

    }
    //矩阵中的路径 DFS LeetCode 79
    private boolean[][] sign79;//访问标志
    public boolean exist(char[][] board, String word) {
        Boolean bo = false;
        sign79 = new boolean[board.length][board[0].length];
        for(int i = 0; i<board.length; i++){
            for(int j = 0; j<board[0].length; j++){
                if(board[i][j] == word.charAt(0)){
                    sign79[i][j] = true;
                    bo = dfs(board,word,i,j,1);
                    sign79[i][j] = false;
                    if(bo) return true;
                }
            }
        }
        return bo;
    }
    public boolean dfs(char[][] board,String word,int i,int j,int index){
        sign79[i][j] = true;
        if(index == word.length())
            return true;
        if((i-1)>=0 && board[i-1][j]==word.charAt(index) && sign79[i-1][j] == false){
            Boolean bo = dfs(board,word,i-1,j,index+1);
            if(bo) return true;
        }
        if((i+1)<board.length && board[i+1][j]==word.charAt(index) && sign79[i+1][j] == false){
            Boolean bo = dfs(board,word,i+1,j,index+1);
            if(bo) return true;
        }
        if((j-1)>=0 && board[i][j-1]==word.charAt(index) && sign79[i][j-1] == false){
            Boolean bo = dfs(board,word,i,j-1,index+1);
            if(bo) return true;
        }
        if((j+1)<board[0].length && board[i][j+1]==word.charAt(index) && sign79[i][j+1] == false){
            Boolean bo = dfs(board,word,i,j+1,index+1);
            if(bo) return true;
        }
        sign79[i][j] = false;
        return false;
    }
    //机器人的运动范围 BFS LeetCode 剑指offer面试题13
    public int movingCount(int m, int n, int k) {
        int[][] matrix = new int[m][n];
        for(int i = 0; i< m; i++){
            for(int j = 0; j<n; j++){
                matrix[i][j] = getNum(i) + getNum(j);
                System.out.print(matrix[i][j]+"\t");
            }
            System.out.println();
        }
        boolean[][] sign = new boolean[m][n];
        bfs(matrix,sign,0,0,k);
        int res = 0;
        for(boolean[] ii: sign)
            for(boolean i:ii)
                if(i)
                    res++;
        return res;
    }
    public void bfs(int[][] matrix,boolean[][] sign,int i,int j,int k){
        sign[i][j] = true;
        if((i-1)>=0 && matrix[i-1][j]<=k && sign[i-1][j] == false){
            bfs(matrix,sign,i-1,j,k);
        }
        if((i+1)<matrix.length && matrix[i+1][j]<=k && sign[i+1][j] == false){
            bfs(matrix,sign,i+1,j,k);
        }
        if((j-1)>=0 && matrix[i][j-1]<=k && sign[i][j-1] == false){
            bfs(matrix,sign,i,j-1,k);
        }
        if((j+1)<matrix[0].length && matrix[i][j+1]<=k && sign[i][j+1] == false){
            bfs(matrix,sign,i,j+1,k);
        }
    }
    public int getNum(int i){
        int res = 0;
        while (i/10!=0){
            res += i%10;
            i = i/10;
        }
        res += i;
        return res;
    }
    //剪绳子 动态规划
    public int cuttingRope(int n) {
        if(n==2) return 1;
        if(n==3) return 2;
        //动态规划
        int[] res = new int[n+1];
        res[1] = 1;
        res[2] = 2;
        res[3] = 3;
        int i = 4;
        while (i<=n){
            for(int j=1;j<i;j++)
                if(((res[j]*res[i-j])%1000000007)>res[i])
                    res[i] = ((res[j]*res[i-j])%1000000007);
            i++;
        }
        for(int ii:res)
            System.out.println(ii);
        return res[n];
    }
    //数值的整数次方 从二进制角度实现快速幂运算 面试题16
    public double myPow(double x, int n) {
        if(x == 0) return 0;
        //Java 代码中n∈[−2147483648,2147483647] ，
        //因此当 n = -2147483648时执行 n = -n会因越界而赋值出错。
        //解决方法是先将 n 存入 long 变量 b。
        long b = n;
        double res = 1.0;
        if(b < 0) {
            x = 1 / x;
            b = -b;
        }
        while(b > 0) {
            if((b & 1) == 1) res *= x;
            x *= x;
            b >>= 1;
        }
        return res;
    }
    //正则表达式匹配 动态规划 面试题19 hard
    public boolean isMatch(String A, String B) {
        int n = A.length();
        int m = B.length();
        boolean[][] f = new boolean[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                //分成空正则和非空正则两种
                if (j == 0) {
                    f[i][j] = i == 0;
                } else {
                    //非空正则分为两种情况 * 和 非*
                    if (B.charAt(j - 1) != '*') {
                        if (i > 0 && (A.charAt(i - 1) == B.charAt(j - 1) || B.charAt(j - 1) == '.')) {
                            f[i][j] = f[i - 1][j - 1];
                        }
                    } else {
                        //碰到 * 了，分为看和不看两种情况
                        //不看
                        if (j >= 2) {
                            f[i][j] |= f[i][j - 2];
                        }
                        //看
                        if (i >= 1 && j >= 2 && (A.charAt(i - 1) == B.charAt(j - 2) || B.charAt(j - 2) == '.')) {
                            f[i][j] |= f[i - 1][j];
                        }
                    }
                }
            }
        }
        return f[n][m];
    }
    //树的子结构 面试题26
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if(B==null||A==null) return false;
        ArrayList<TreeNode> list = new ArrayList<>();
        travelA(A,list,B.val);
        boolean res;
        for(TreeNode node : list){
            res = travel(node,B);
            if(res) return true;
        }
        return false;
    }
    private void travelA(TreeNode A,ArrayList<TreeNode> list,int x){
        if(A != null){
            if(A.val==x) list.add(A);
            travelA(A.left,list,x);
            travelA(A.right,list,x);
        }
    }
    private boolean travel(TreeNode a,TreeNode b){
        if(b==null) return true;
        if(a==null) return false;
        if(a.val!=b.val) return false;
        boolean r1=false;
        boolean r2=false;
        r1 = travel(a.left,b.left);
        r2 = travel(a.right,b.right);
        return (r1&&r2);
    }
    //对称的二叉树 面试题28
    public boolean isSymmetric(TreeNode root) {
        return isHelper(root,root);

    }
    private boolean isHelper(TreeNode n1, TreeNode n2) {
        if((n1==null)^(n2==null)) return false;
        if(n1!=null && n2!=null){
            if(n1.val==n2.val){
                boolean a,b=false;
                a = isHelper(n1.right,n2.left);
                b = isHelper(n2.left,n1.right);
                return a&b;
            }else
                return false;
        }
        return true;
    }
    //顺时针打印矩阵 面试题29
    private int[] spiralOrder(int[][] matrix) {
        if(matrix.length==0) return new int[0];
        int[] res = new int[matrix.length*matrix[0].length];
        int index=0,i=-1,j=-1,iMin=1,jMin=0,iMax = matrix.length,jMax = matrix[0].length;
        while (index<res.length){
            for(i++,j++;j<jMax;j++)
                res[index++] = matrix[i][j];
            jMax--;
            if(index==res.length) break;
            for(i++,j--;i<iMax;i++)
                res[index++] = matrix[i][j];
            iMax--;
            if(index==res.length) break;
            for(j--,i--;j>=jMin;j--)
                res[index++] = matrix[i][j];
            jMin++;
            if(index==res.length) break;
            for(i--,j++;i>=iMin;i--)
                res[index++] = matrix[i][j];
            iMin++;
            if(index==res.length) break;
        }
        return res;
    }
    //栈的压入、弹出序列 面试题31
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        int i = 0,j=0;
        Stack<Integer> st = new Stack<>();
        while (true){
            if(i<pushed.length && (st.empty() || st.peek()!=popped[j])){
                st.push(pushed[i++]);
            }
            else if(!st.empty() && st.peek()==popped[j]){
                st.pop();
                j++;
            }else break;
        }
        return j==popped.length;
    }
    //从上到下打印二叉树 面试题32
    public List<List<Integer>> levelOrder(TreeNode root) {
        ArrayList<List<Integer>> res = new ArrayList<>();
        if(root == null) return res;
        LinkedList<TreeNode> list = new LinkedList<>();
        list.add(root);
        ArrayList<Integer> level;
        boolean s = false;
        while (!list.isEmpty()){
            int len = list.size();
            level = new ArrayList<>();
            for(int i=0;i<len;i++){
                TreeNode node = list.removeFirst();
                if(node.left!=null) list.add(node.left);
                if(node.right!=null) list.add(node.right);
                level.add(node.val);
            }
            if(s)
                Collections.reverse(level);
            res.add(level);
            s = !s;
        }
        return res;
    }
    //二叉搜索树的后序遍历 面试题33
    public boolean verifyPostorder(int[] postorder) {
        /*二叉搜索树 后序遍历的特点：{比x小的序列，比x大的序列，x}*/
        if(postorder.length==0)
            return true;
//        System.out.println(Arrays.toString(order));
        int midNum = postorder[postorder.length-1];
        int midIndex = 0; //比根节点大的第一个数的下标
        //找到midIndex
        for(int i=0;i<postorder.length-1;i++){
            if(midNum<postorder[i]){
                midIndex = i;
                break;
            }
            if(i==postorder.length-2){
                midIndex = i+1;
                break;
            }
        }
        //判断是否为后序遍历
        for(int i=0;i<postorder.length-1;i++)
            if( !((postorder[i]<midNum && i<midIndex) || (postorder[i]>midNum && i>=midIndex)) )
                return false;
        //赋值用来递归
        int[] preorder = new int[midIndex];
        int[] aftorder = new int[postorder.length-1-midIndex];
        for(int i=0;i<midIndex;i++){
            preorder[i] = postorder[i];
        }
        for(int i=0;i<postorder.length-1-midIndex;i++){
            aftorder[i] = postorder[midIndex+i];
        }
        boolean t1 = verifyPostorder(preorder);
        boolean t2 = verifyPostorder(aftorder);
        return t1&&t2;
    }
    ////二叉搜索树的后序遍历 面试题33 解法2
    public boolean verifyPostorder2(int[] postorder) {
        return recur(postorder, 0, postorder.length - 1);
    }
    boolean recur(int[] postorder, int i, int j) {
        if(i >= j) return true;
        int p = i;
        while(postorder[p] < postorder[j]) p++;
        int m = p;
        while(postorder[p] > postorder[j]) p++;
        return p == j && recur(postorder, i, m - 1) && recur(postorder, m, j - 1);
    }
    //二叉树中和为某一值的路径 面试题34
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        ArrayList<List<Integer>> reslist = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        if(root==null) return reslist;
        pathSumHelper(root,list,reslist,0,sum);
        return reslist;
    }
    private void pathSumHelper(TreeNode node,ArrayList<Integer> list,List<List<Integer>> reslist,int num,int sum){
        if(node.left == null && node.right==null){
            list.add(node.val);
            if((num+node.val)==sum)
                reslist.add(new ArrayList<>(list));
            list.remove(list.size()-1);
            return;
        }
        list.add(node.val);
        if(node.left!=null) pathSumHelper(node.left,list,reslist,num+node.val,sum);
        if(node.right!=null) pathSumHelper(node.right,list,reslist,num+node.val,sum);
        list.remove(list.size()-1);
    }
    //复杂链表的复制 面试题35 采用Hashmap方法，易理解
    public Node copyRandomList(Node head) {
        HashMap<Node,Node> map = new HashMap<>();//
        Node cur = head;
        //复制节点值
        while (cur!=null){
            map.put(cur,new Node(cur.val));
            cur = cur.next;
        }
        //复制节点指向
        cur = head;
        while (cur!=null){
            Node cloneNext = map.get(cur.next);
            Node cloneRandom = map.get(cur.random);
            Node clone = map.get(cur);
            clone.random = cloneRandom;
            clone.next = cloneNext;
            cur = cur.next;
        }
        return map.get(head);
    }
    //二叉搜索树与双向链表 面试题36
    public Node1 treeToDoublyList(Node1 root) {
        if(root==null)  return root;
        //将搜索树与中序遍历联系起来
        ArrayList<Node1> list = new ArrayList<>();
        treeToDoublyListHelper(root,list);
        //改变节点指向
        for (int i=0;i<list.size();i++){
            Node1 node = list.get(i);
            node.left = list.get((list.size()+i-1)%list.size());
            node.right = list.get((list.size()+i+1)%list.size());
        }
        return list.get(0);
    }
    private void treeToDoublyListHelper(Node1 node,ArrayList<Node1> list){
        if(node == null) return;
        treeToDoublyListHelper(node.left,list);
        list.add(node);
        treeToDoublyListHelper(node.right,list);
    }
    //序列化二叉树 面试题37 借助队列实现层次遍历
    public class Codec {
        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if(root==null) return "[]";
            ArrayList<TreeNode> reslist = new ArrayList<>();
            LinkedList<TreeNode> levellist = new LinkedList<>();
            reslist.add(root);
            levellist.add(root);
            while (true){
                int len = levellist.size();//树每一层的长度
                boolean sign = false;
                //按层添加节点到reslist
                for(int i=0;i<len;i++){
                    if(levellist.getFirst()!=null){
                        sign = true;
                        levellist.add(levellist.getFirst().left);
                        levellist.add(levellist.getFirst().right);
                    }
                    levellist.removeFirst();
                }
                if(!sign) break;
                reslist.addAll(levellist);
            }
            while (reslist.get(reslist.size()-1)==null)
                reslist.remove(reslist.size()-1);
            //将reslist编写成String形式
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for(TreeNode treeNode:reslist){
                if(treeNode==null)
                    sb.append("null");
                else
                    sb.append(treeNode.val);
                sb.append(",");
            }
            sb.replace(sb.length()-1,sb.length(),"]");
            return sb.toString();
        }
        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if(data.length()<3) return null;
            data = data.substring(1,data.length()-1);
            String[] s = data.split(","); //结果例如：{"5","2","3","null","null","2","4","3","1"}
            TreeNode head = new TreeNode(Integer.parseInt(s[0]));
            ArrayList<TreeNode> list = new ArrayList<>();
            list.add(head);
            int index = 1;//数组s的下标
            while (true){
                TreeNode node = list.get(0);
                //左节点
                if(index==s.length) break;
                if(!s[index++].equals("null")){
                    node.left = new TreeNode(Integer.parseInt(s[index-1]));
                    list.add(node.left);
                }
                //右节点
                if(index==s.length) break;
                if(!s[index++].equals("null")){
                    node.right = new TreeNode(Integer.parseInt(s[index-1]));
                    list.add(node.right);
                }
                list.remove(0);
            }
            return head;
        }
    }
    //字符串的排列 面试题38 回溯+剪枝(包含重复元素需要进行剪枝)
    public String[] permutation(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);//排序方便“剪枝”
        boolean[] used = new boolean[s.length()];
        StringBuilder sb = new StringBuilder();
        ArrayList<String> res = new ArrayList<>();
        permutationHelper(chars,used,sb,res);
        return res.toArray(new String[0]);
    }
    private void permutationHelper(char[] chars, boolean[] used, StringBuilder sb,ArrayList<String> res) {
        if(sb.length()==chars.length){
            res.add(sb.toString());
            return;
        }
        for(int i=0;i<chars.length;i++){
            if(!used[i]){
                //“剪枝”的思路：在一定会产生重复结果集的地方剪枝。
                //i>0是为了保证chars[i-1]有意义；used[i-1]是因为chars[i-1]在回退的过程中刚刚被撤销选择
                if (i > 0 && chars[i] == chars[i - 1] && !used[i - 1]) {
                    continue;
                }
                //做相应标记，进入下一个递归
                used[i] = true;
                sb.append(chars[i]);
                permutationHelper(chars,used,sb,res);
                // 递归完成以后，需要撤销选择，递归方法执行之前做了什么，递归方法执行以后就需要做相应的逆向操作
                sb.deleteCharAt(sb.length()-1);
                used[i] = false;
            }
        }
    }
    //数据流中的中位数 面试题41 优先队列
    class MedianFinder {
        Queue<Integer> A, B;
        public MedianFinder() {
            A = new PriorityQueue<>(); // 小顶堆，保存较大的一半
            B = new PriorityQueue<>((x, y) -> (y - x)); // 大顶堆，保存较小的一半
        }
        public void addNum(int num) {
            if(A.size() != B.size()) {
                A.add(num);
                B.add(A.poll());
            } else {
                B.add(num);
                A.add(B.poll());
            }
        }
        public double findMedian() {
            return A.size() != B.size() ? A.peek() : (A.peek() + B.peek()) / 2.0;
        }
    }
    //最多可参加会议数LeetCode 1353 优先队列+贪心算法
    public int maxEvents(int[][] events) {
        Arrays.sort(events, new Comparator<int[]>(){
            @Override
            public int compare(int[] a, int[] b){
                return a[0] - b[0];
            }
        });
        PriorityQueue<Integer> queue = new PriorityQueue<>();//存放的是会议的结束时间
        int day = 0, id = 0, n = events.length, res = 0;//day就代表第day天，id代表会议下标
        while(id < n || !queue.isEmpty()){
            if(queue.isEmpty()){
                queue.add(events[id][1]);
                day = events[id++][0];
            }
            //把所有 开会时间<=day 的会议的结束时间加入优先队列
            while(id < n && events[id][0] <= day){
                queue.add(events[id++][1]);
            }
            //会议结束时间>=day
            if(queue.peek() >= day){
                day++;
                res++;
            }
            queue.poll();
        }
        return res;
    }
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0) return new int[0];
        int[] res = new int[nums.length - k + 1];
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 0; i < k; i++)
            queue.add(nums[i]);
        for (int i = 0; i < res.length - 1; i++) {
            res[i] = queue.peek();
            queue.remove(nums[i]);
            queue.add(nums[i + k]);
        }
        res[res.length - 1] = queue.peek();
        return res;
    }
    //圆圈中最后剩下的数字 面试题62 约瑟夫环问题
    public int lastRemaining(int n, int m) {
        int ans = 0;
        // 最后一轮剩下2个人，所以从2开始反推
        for (int i = 2; i <= n; i++) {
            ans = (ans + m) % i;
        }
        return ans;
    }
    //股票的最大利润 面试题63 DP
    public int maxProfit(int[] prices) {
        if(prices.length==0) return 0;
        int minNum = prices[0];
        int res = 0;
        for(int i=0;i<prices.length;i++){
            if(prices[i]<=minNum)
                minNum = prices[i];
            if(prices[i]-minNum>=res)
                res = prices[i]-minNum;
        }
        return res;
    }
    //不使用* / for if实现1+2+...+n 面试题64
    public int sumNums(int n) {
        boolean x = n > 1 && (n += sumNums(n - 1)) > 0;
        return n;
    }
    //不用加减乘除做加法 面试题65 位运算的应用：a+b = (a^b) + ((a&b)<<1)
    public int add(int a, int b) {
        //a+b = (a^b) + ((a&b)<<1)
        while(b != 0) { // 当进位为 0 时跳出
            int c = (a & b) << 1;  // c = 进位
            a ^= b; // a = 非进位和
            b = c; // b = 进位
        }
        return a;
    }
    //构建乘积数组 面试题66 分治思想
    public int[] constructArr(int[] a) {
        if (a.length == 0) {
            return new int[0];
        }
        int[] res = new int[a.length];
        int left = 1;
        for (int i = 0; i < res.length; i++) {
            res[i] =left;
            left *= a[i];
        }
        int right = 1;
        for (int i = res.length - 1; i >= 0; i--) {
            res[i] *= right;
            right *= a[i];
        }
        return res;
    }
    //把字符串转换成整数 面试题67
    public int strToInt(String str) {
        if(str.equals("")) return 0;
        char[] c = str.toCharArray();
        int sign = 1;
        long res = 0;
        int i = 0;
        //找到非空的第一个字符
        while (c[i]==' ')
            if(++i==c.length)
                return 0;
        //判断是否有符号
        if(c[i]=='+') {
            sign = 1;
            i++;
        }
        else if(c[i]=='-') {
            sign = -1;
            i++;
        }
        //依次转换为整数
        for(;i<c.length;i++){
            if(c[i]>='0' && c[i]<='9'){
                res = res*10 + sign*(c[i]-'0');
                if(res>Integer.MAX_VALUE)
                    return Integer.MAX_VALUE;
                else if(res<Integer.MIN_VALUE)
                    return Integer.MIN_VALUE;
            }else
                break;
        }
        return (int) res;
    }
    //95. 不同的二叉搜索树II
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new LinkedList<TreeNode>();
        }
        return generateTrees(1, n);
    }
    public List<TreeNode> generateTrees(int start, int end) {
        List<TreeNode> allTrees = new LinkedList<>();
        if (start > end) {
            allTrees.add(null);
            return allTrees;
        }
        // 枚举可行根节点
        for (int i = start; i <= end; i++) {
            // 获得所有可行的左子树集合
            List<TreeNode> leftTrees = generateTrees(start, i - 1);
            // 获得所有可行的右子树集合
            List<TreeNode> rightTrees = generateTrees(i + 1, end);
            // 从左子树集合中选出一棵左子树，从右子树集合中选出一棵右子树，拼接到根节点上
            for (TreeNode left : leftTrees) {
                for (TreeNode right : rightTrees) {
                    TreeNode currTree = new TreeNode(i);
                    currTree.left = left;
                    currTree.right = right;
                    allTrees.add(currTree);
                }
            }
        }
        return allTrees;
    }
    //next

}

