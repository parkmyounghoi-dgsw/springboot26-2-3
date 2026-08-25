import java.util.Arrays;
import java.util.List;

public class Ex02 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("곱하기전...");
        list.forEach(num -> System.out.println(num));
        list.replaceAll(num -> num * num);
        System.out.println("곱하기후...");
        list.forEach(num -> System.out.println(num));
    }
}
