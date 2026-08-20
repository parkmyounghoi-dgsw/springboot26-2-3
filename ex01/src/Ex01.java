import java.util.Optional;

public class Ex01 {
    public static void main(String[] args) {
        // Optinal 생성 방법 3가지
        int a = 10;
        // 10 of는 null을 허용하지 않고 사용한다.
        Optional<Integer> intopt = Optional.of(10);
        // opNullable 은 null 일수도 있고 값일 수도 있다.
        Optional<String> stropt = Optional.ofNullable(null);
        // empty는 null 이다.
        Optional<String> stropt2 = Optional.empty();

        // Optinal get orElse ifpresent
        System.out.println(intopt.get());
        System.out.println(stropt.orElse("기본값설정"));
        if(stropt2.isPresent()) {
            System.out.println(stropt2.get());
        }else{
            System.out.println("값이 없음.");
        }
//        System.out.println(stropt2.get());

    }
}
