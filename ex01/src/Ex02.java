import java.util.Optional;

public class Ex02 {
    // 한줄 삭제 ctrl + x
    // 자동 정렬이 option + command + l
    public static void main() {
        // Optinal 안에 hello 담아서 꺼내서 출력하기
        Optional<String> qq = Optional.of("Hello");
        System.out.println(qq.get());

        // Optinal 안에 빈값을 담고 값이 없으면 빈값을 출력하라.
        Optional<String> empty = Optional.empty();
        System.out.println(empty.orElse("빈 값"));

        System.out.println(Optional.ofNullable(null).orElse("기본값"));

    }
}