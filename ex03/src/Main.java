@FunctionalInterface    // 추상 메서드 한개 존재...
interface AA{
    void doA();
}
@FunctionalInterface
interface BB{
    void doB(int a);
}
@FunctionalInterface
interface CC{
    Boolean doC(String a, String b);
}
@FunctionalInterface
interface DD{
    String doD(String a, String b);
}
public class Main {
    public static void main(String[] args) {
        AA a = ()->{
            System.out.println("AA.doA 메서드");
        };
        a.doA();
        BB b = (num)->{
            System.out.println("BB doB 메서드 "+num);
        };
        b.doB(10);
    }
}