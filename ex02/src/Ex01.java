interface DoA{
//    void doSomething();
    void doA();
    default void doB(){
        System.out.println("doB");
    }
    static void doC(){
        System.out.println("doC");
    }
}
public class Ex01 {
    public static void main(String[] args) {
        DoA doA = new DoA() {
            public void doA() {
                System.out.println("test1111");
            }
//            public void doSomething() {}
        };
        DoA d1 = ()->{
            System.out.println("test2222");
        };
        doA.doA();
        d1.doA();
        d1.doB();   // default Method 호출
        DoA.doC(); // static Method 호출
    }
}
