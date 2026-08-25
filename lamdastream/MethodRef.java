import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

// 별도의 일반 클래스 (이너 클래스 X)
class Person {
    private String name;

    // 1. 기본 생성자 (Supplier용)
    public Person() {
        this.name = "익명";
    }

    // 2. 매개변수가 있는 생성자 (Function용)
    public Person(String name) {
        this.name = name;
    }

    // 3. 정적 메서드 (Predicate용)
    public static boolean isAdult(int age) {
        return age >= 19;
    }

    public String getName() {
        return name;
    }
}

public class MethodRef {
    public static void main(String[] args) {

        // ==========================================
        // 1. Supplier<T> : 입력 X, 출력 T
        // 생성자 참조 (기본 생성자)
        // ==========================================
        // 람다: Supplier<Person> s = () -> new Person();
        Supplier<Person> personSupplier = Person::new;
        Person p1 = personSupplier.get();


        // ==========================================
        // 2. Function<T, R> : 입력 T, 출력 R
        // 생성자 참조 (매개변수 있는 생성자)
        // ==========================================
        // 람다: Function<String, Person> f = name -> new Person(name);
        Function<String, Person> personFunction = Person::new;
        Person p2 = personFunction.apply("홍길동");


        // ==========================================
        // 3. Consumer<T> : 입력 T, 출력 void
        // System.out.println 메서드 참조
        // ==========================================
        // 람다: Consumer<String> c = str -> System.out.println(str);
        Consumer<String> printer = System.out::println;

        printer.accept("=== 결과 출력 ===");
        printer.accept("Supplier로 만든 객체 이름: " + p1.getName());
        printer.accept("Function으로 만든 객체 이름: " + p2.getName());


        // ==========================================
        // 4. Predicate<T> : 입력 T, 출력 boolean
        // 정적(static) 메서드 참조
        // ==========================================
        // 람다: Predicate<Integer> p = age -> Person.isAdult(age);
        Predicate<Integer> adultCheck = Person::isAdult;

        printer.accept("20세 성인 여부: " + adultCheck.test(20));
        printer.accept("15세 성인 여부: " + adultCheck.test(15));
    }
}