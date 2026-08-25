import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Ex04 {

    public static void main(String[] args) {

        Function<String, Integer> f1 = i -> Integer.parseInt(i);
        Function<String, Integer> f2 = Integer::parseInt;

        List<String> al = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");

//        al.forEach(s-> System.out.println(s));
        al.forEach(System.out::println);

        // interface Calculate @FunctionalInterface를 만들어서
        // 추상메서드 operate를 추가 하고
        // 덧셈하는 람다식
        // 뺄셈하는 람다식을 작성해서
        // 두수를 보내 더하기와 빼기를 구하시오 (출력)

        // 스트림 시작해야 함

    }

}
