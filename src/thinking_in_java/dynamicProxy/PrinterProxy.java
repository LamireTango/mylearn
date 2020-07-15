package thinking_in_java.dynamicProxy;
//静态代理实现print前"记录日志"

class PrinterProxy implements IPrinter {
    private IPrinter printer;
    public PrinterProxy(){
        this.printer = new Printer();
    }
    @Override
    public void print() {
        System.out.println("记录日志");
        printer.print();
    }

    public static void main(String[] args) {
        PrinterProxy proxy = new PrinterProxy();
        proxy.print();
    }
}
//如果存在多个接口，IPrinter,IPrinterln，IPrinterInt等，都要实现
//System.out.println("记录日志");这个功能呢？
//需要用到动态代理