// 메서드에 매개변수가...    기본자료형, 객체자료형,, 함수자료형타입... React js...
interface BB{
    String doA(int a);
}
class AAAA{}
public class Ex04 {
    /*
        const fu = (aa,bb)=>{
        }
        fu( ()=>{}, {} );
     */
    public static void method1(BB bb, AAAA aaaa){
        String test = bb.doA(100);
        System.out.println(test);
    }

    public static void main(String[] args) {
        method1(
                (num)-> String.valueOf(num) ,
                new AAAA()
        );
    }
}
