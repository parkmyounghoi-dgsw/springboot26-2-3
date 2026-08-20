import java.util.Optional;
class Main {
    public static Optional<String> getName(){
        return Optional.of("홍길동");
    }
    public static void main(String[] args) {
        System.out.println("안녕하세요 박명회입니다.");
        String name = null;
        try {
            if(name != null) {
                System.out.println("name.length()" + name.length());
            }
        }catch (Exception e){
//            e.printStackTrace();
        }
        System.out.println("실행");
        Optional<String> optional = getName();
        optional.ifPresent(s->System.out.println(s.length()));
    }
}
