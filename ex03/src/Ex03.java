import java.util.function.Function;

public class Ex03 {

    public static void main(String[] args) {
        // 문자열을 16진수로 변환
        Function<String, Integer> f = (s) -> Integer.parseInt(s, 16);
        // 숫자를 2진수 문자열로 반환
        Function<Integer, String> f2 = (i) -> Integer.toString(i, 2);

        Function<String, String> andThenf = f.andThen(f2);
        System.out.println(andThenf.apply("10"));
    }
}
