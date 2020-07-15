package thinking_in_java.from1to11;/*Java编程思想 银行出纳员仿真21.8*/
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 消费者（客户）
 *
 * @author Administrator
 *
 */
class Customer {
    private final int serviceTime;// 服务时间（客户的业务办理时间）

    public Customer(int tm) {
        serviceTime = tm;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public String toString() {
        return " ● ";
    }
}

/**
 * 消费者队列（客户排成一条队伍）
 *
 * @author Administrator
 *
 */
class CustomerLine extends ArrayBlockingQueue<Customer> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 构造方法传入客户最大人数
     *
     * @param maxLineSize
     */
    public CustomerLine(int maxLineSize) {
        super(maxLineSize);
    }

    /**
     * 返回所有客户（服务时间）
     */
    public String toString() {
        if (this.size() == 0)
            return "[Empty]";

        StringBuilder result = new StringBuilder();
        for (Customer customer : this) {
            result.append(customer);
        }
        return result.toString();
    }
}

/**
 * 消费者产生器
 *
 * @author Administrator
 *
 */
class CustomerGenerator implements Runnable {
    private CustomerLine customers;
    private static Random rand = new Random(47);

    public CustomerGenerator(CustomerLine cq) {
        customers = cq;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                // 每隔300MILLS就假如一个客户（来办理业务等）
                TimeUnit.MILLISECONDS.sleep(rand.nextInt(300));
                // 将客户加入到消费者队列
                customers.put(new Customer(rand.nextInt(1000)));
                // System.out.println(customers.toString());
            }
        } catch (InterruptedException e) {
            System.out.println("thinking_in_java.from1to11.CustomerGenerator interrupted");
        }
        System.out.println("thinking_in_java.from1to11.CustomerGenerator terminating");
    }
}

/**
 * 出纳员，业务员
 *
 * @author Administrator
 *
 */
class Teller implements Runnable, Comparable<Teller> {
    private static int counter = 0;
    private final int id = counter++;// 业务员的id
    private int customersServed = 0;// 已经服务的客户
    private CustomerLine customers;// 客户队列
    private boolean servingCustomerLine = true;// 是否正在服务客户
    private int count = 0;
    private boolean sort = true;// 排序方式，true为降序，false升序

    public Teller(CustomerLine cq) {
        customers = cq;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // 下面两行代表正在为客户服务
                Customer customer = customers.take();
                count++;
                TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());
                synchronized (this) {// 这里同步的原因是因为禁止执行到这里时线程调用doSomethingElse(),serveCustomerLine(),以及compareTo(Tellero)
                    customersServed++;// 服务的客户+1
                    while (!servingCustomerLine)
                        // 当出纳员去干其他的事情的时候servingCustomerLine会为false
                        wait();// 代表出纳员正在做其他事情
                }
            }
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
        System.out.println(this + " terminating");// 结束
    }

    /**
     * 出纳员做其他的事情
     */
    public synchronized void doSomethingElse() {
        // customersServed = 0;//服务过的客户置0
        servingCustomerLine = false;
    }

    /**
     * 出纳员服务客户队列
     */
    public synchronized void serveCustomerLine() {
        assert !servingCustomerLine : "already serving:" + this;
        servingCustomerLine = true;// 标识修改
        notifyAll();// 唤醒线程
    }

    public String toString() {
        return "thinking_in_java.from1to11.Teller " + id + "：" + count;
    }

    public String shortString() {
        return "T" + id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSort() {
        return sort;
    }

    public void setSort(boolean sort) {
        this.sort = sort;
    }

    /**
     * 优先级队列的优先级比较方法
     */
    @Override
    public synchronized int compareTo(Teller o) {
        // TODO Auto-generated method stub
        if (sort)// 降序(目的为了加入到workingTellers队列中)
            return o.customersServed - customersServed > 0 ? 1
                    : o.customersServed - customersServed < 0 ? -1 : 0;
        else
            // 升序(目的为了加入到tellersDoingOtherThings队列中)
            return customersServed - o.customersServed > 0 ? 1
                    : customersServed - o.customersServed < 0 ? -1 : 0;
    }

}

/**
 * 出纳员管理器
 *
 * @author Administrator
 *
 */
class TellerManager implements Runnable {
    private ExecutorService exec;// 执行器
    private CustomerLine customers;// 客户队列
    private PriorityQueue<Teller> workingTellers = new PriorityQueue<Teller>();// 降序队列
    private PriorityQueue<Teller> tellersDoingOtherThings = new PriorityQueue<Teller>();// 升序队列
    private int adjustmentPeriod;// 调整周期

    public TellerManager(ExecutorService e, CustomerLine customers,
                         int adjustmentPeriod) {
        exec = e;
        this.customers = customers;
        this.adjustmentPeriod = adjustmentPeriod;
        // 构造出纳员管理器的时候默认新增一个出纳员
        Teller teller = new Teller(customers);// 默认为降序
        exec.execute(teller);
        workingTellers.add(teller);
    }

    /**
     * 调整出纳员数量
     * */
    public void adjustTellerNumber() {
        try {
            // 利用反射调用PriorityQueue的heapify（刷新队列头）方法
            Method method = workingTellers.getClass().getDeclaredMethod(
                    "heapify");
            method.setAccessible(true);
            try {
                method.invoke(workingTellers);
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 如果客户队列的size大于出纳员队列的size的两倍，新增出纳员
        if (customers.size() / workingTellers.size() > 2) {
            if (tellersDoingOtherThings.size() > 0) {
                Teller teller = tellersDoingOtherThings.remove();
                teller.setSort(true);// 设置为降序
                teller.serveCustomerLine();
                workingTellers.add(teller);
                return;
            }
            Teller teller = new Teller(customers);// 默认为降序
            exec.execute(teller);
            workingTellers.add(teller);
            return;
        }
        // 如果出纳员的size大于1并且客户队列的size小雨出纳员size的两倍，调用一个出纳员去做其他的事情，从出纳员队列中移除
        if (workingTellers.size() > 1
                && customers.size() / workingTellers.size() < 2)
            reassignOneTeller();
        // 如果客户队列size==0并且出纳员的size大于1，同样移除一个出纳员去做其他的事情
        if (customers.size() == 0)
            while (workingTellers.size() > 1)
                reassignOneTeller();
    }

    /**
     * 移除一个出纳员去做其他事情
     * */
    private void reassignOneTeller() {
        Teller teller = workingTellers.poll();
        teller.setSort(false);
        teller.doSomethingElse();
        tellersDoingOtherThings.add(teller);
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                adjustTellerNumber();
                TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);
                // 打印客户和出纳员队列
                System.out.print("customers[" + customers
                        + "]　　　　workingTellers[");
                for (Teller teller : workingTellers) {
                    System.out.print(teller.shortString() + ":"
                            + teller.getCount() + "  ");
                }
                // 打印在做其他事情的出纳员队列
                System.out.print("]　　　　doingOtherThingTellers[");
                for (Teller teller : tellersDoingOtherThings) {
                    System.out.print(teller.shortString() + ":"
                            + teller.getCount() + "  ");
                }
                if (tellersDoingOtherThings.size() == 0)
                    System.out.print("Empty");
                System.out.println("]\n");
            }
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
        System.out.println(this + " terminating");
    }

    public String toString() {
        return "thinking_in_java.from1to11.TellerManager";
    }
}

public class BankTellerSimulation {
    static final int MAX_LINE_SIZE = 50;
    static final int ADJUSTMENT_PERIOD = 1000;

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        CustomerLine customers = new CustomerLine(MAX_LINE_SIZE);
        exec.execute(new CustomerGenerator(customers));
        exec.execute(new TellerManager(exec, customers, ADJUSTMENT_PERIOD));
        if (args.length > 0)
            TimeUnit.SECONDS.sleep(new Integer(args[0]));
        else {
            System.out.println("Press 'Enter' to quit");
            System.in.read();
        }
        exec.shutdownNow();
    }
}