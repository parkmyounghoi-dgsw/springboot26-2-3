void main() {

    Optional<String> java = Optional.of("java");
    System.out.println(java.get());

    String temp = java.orElse("값이 없다");
    System.out.println(temp.length());

}
