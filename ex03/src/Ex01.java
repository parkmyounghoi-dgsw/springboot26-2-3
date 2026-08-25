import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

//interface Sup<T>{
//    T get();
//}
public class Ex01 {
    public static void main(String[] args) {
        // shift shift 찾고자 하는 거 ...
        // commnad + e 작업했던 파일 목록순으로 나와요..
        Supplier<Integer> s = () -> (int) (Math.random() * 100);
        System.out.println(s.get());
        List<Integer> list = new ArrayList<>();
        makeRandomList(s, list);
        System.out.println(list);
        // 소비 반환값이 없고 매개변수를 받아 소비 하는 역활...
        Consumer<Integer> c = x -> System.out.println(x);
        c.accept(100);
        printList(c, list);

        Function<Integer, String> f = x -> x + " 두번 더하면 " + (x + x) + " 입니다";
        System.out.println(f.apply(10));

        Predicate<String> pr = s1 -> s1.equals("abcd");
        System.out.println(pr.test("abcd"));
        System.out.println(pr.test("aaaa"));

        BiConsumer<String, String> bic = (x, y) -> {
            System.out.println("이름은 " + x + "입니다.");
            System.out.println("나이는 " + y + "입니다.");
        };
        bic.accept("홍길동","30");
    }

    // command 화살표 오른쪽 문장 제일 끝으로
    private static void printList(Consumer<Integer> c, List<Integer> list) {
        list.forEach(c);
    }

    public static <T> void makeRandomList(Supplier<Integer> s, List<Integer> list) {
//        list.add(s.get()); list.add(s.get());
        for (int i = 0; i < 10; i++)
            list.add(s.get());
    }
}
