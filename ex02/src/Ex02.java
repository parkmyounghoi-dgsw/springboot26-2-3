public class Ex02 {
    static void main(String[] args) {
        AA a1 = ()-> 10;
        AA a2 = ()-> 20;
        System.out.println(a1.doA());
        System.out.println(a2.doA());
    }
}

@FunctionalInterface
interface AA{
    int doA();
//    int doB();
}