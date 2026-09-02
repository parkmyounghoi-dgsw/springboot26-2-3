package com.dgsw.ex04.lambda;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
public class LambdaController {

    @GetMapping("lambda")
    public String lambda() {
        return "lambda";
    }

    @GetMapping("streammake")
    public String streammake() {
        String[] strings = new String[]{"bb", "aa", "dd", "cc", "eee", "kkk", "oo"};
        Stream<String> stream = Stream.of(strings);
        long count = stream.filter(s -> s.length() == 2)
                .sorted()
                .peek(System.out::println)
                .count();
        return "streammake count = " + count;
    }

    @GetMapping("streamMap")
    public String streamMap() {
        String[] arr = {"aa", "bb", "cc", "ddd", "eee"};
        Stream<String> stream = Stream.of(arr);
        List<Integer> list = stream
                .map((str) -> str.length())
                .toList();
        System.out.println(list);
        return "streamMap count = " + list;
    }

    // calculate 인터페이스 만들어 가지고
    // 람다 정의 해서
    // return 으로 두수의 합과 차를 구해보자.
    @GetMapping("calculate")
    public Map<String, Integer> calculate(@RequestParam int a, @RequestParam int b) {
        System.out.println("a:" + a + " b:" + b);
        Calculate add = (a1, b1) -> a1 + b1;
        Calculate sub = (a1, b1) -> a1 - b1;
        return Map.of("a", a,
                "b", b,
                "a+b", add.calcu(a, b),
                "a-b", sub.calcu(a, b));
    }

    // 스트림.. ai 를 사용하지 말고 하세요..
    // swagger 문서에서 테스트 할수 있도록...
    // url even을 호출 하고...
    // @RequestParam from @RequestParam to 두 변수를 받아서 intStream.range(from,to) 를 사용해서
    // 짝수를 filter 하고... count 개수를 리턴하는
    // map.of( "짝수 count": "몇개", "filter", list )
}

@FunctionalInterface
interface Calculate {
    int calcu(int a, int b);
}