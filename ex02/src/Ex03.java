import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ex03 {
    static void main(String[] args) {
        List<String> list = Arrays.asList("abc","abcd","aa","aaa","abcde","bbb","ccc","ddd");
//        Collections.sort(list, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o2.compareTo(o1);
//            }
//        });
//        Collections.sort(list, (o1, o2) -> o2.compareTo(o1));
        Collections.sort(list, (o1, o2) -> o2.length() - o1.length());
        System.out.println(list);
    }
}
