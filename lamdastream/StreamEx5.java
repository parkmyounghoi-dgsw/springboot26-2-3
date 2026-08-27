import java.util.*;
import java.util.stream.*;

class StreamEx5 {
	public static void main(String[] args) {
		// 1. 테스트용 문자열 배열 정의
		String[] strArr = {
				"Inheritance", "Java", "Lambda", "stream",
				"OptionalDouble", "IntStream", "count", "sum"
		};

		// 배열 요소 전체 출력 (최종 연산)
		Stream.of(strArr).forEach(System.out::println);

		// 2. 조건 검사 매칭 메서드: noneMatch()
		// 모든 요소가 조건(길이가 0)을 만족하지 않으면 true 반환 (빈 문자열이 없는지 확인)
		boolean noEmptyStr = Stream.of(strArr).noneMatch(s -> s.length() == 0);

		// 3. 요소 검색 메서드: findFirst()
		// 첫 글자가 's'로 시작하는 첫 번째 요소를 찾아서 Optional<String>으로 반환
		Optional<String> sWord = Stream.of(strArr)
				.filter(s -> s.charAt(0) == 's')
				.findFirst();

		System.out.println("noEmptyStr=" + noEmptyStr);
		System.out.println("sWord=" + sWord.get()); // Optional 내부 값 추출 ("stream")

		// 4. Stream<String>을 기본형 스트림 IntStream으로 변환 (각 단어의 길이)
		// 스트림은 일회성이므로 여러 reduce 연산을 위해 별도로 생성
		IntStream intStream1 = Stream.of(strArr).mapToInt(String::length);
		IntStream intStream2 = Stream.of(strArr).mapToInt(String::length);
		IntStream intStream3 = Stream.of(strArr).mapToInt(String::length);
		IntStream intStream4 = Stream.of(strArr).mapToInt(String::length);

		// 5. reduce()를 이용한 다양한 축소(Reduction) 연산
		// (1) 요소 개수 구하기: 초기값 0에서 시작하여 각 요소마다 1씩 더함
		int count = intStream1.reduce(0, (a, b) -> a + 1);

		// (2) 요소 전체 합 구하기: 초기값 0에서 시작하여 각 길이(b)를 누적해서 더함
		int sum   = intStream2.reduce(0, (a, b) -> a + b);

		// (3) 최댓값 구하기: 초기값이 없으므로 결과가 빈 스트림일 경우를 대비해 OptionalInt 반환
		OptionalInt max = intStream3.reduce(Integer::max);

		// (4) 최솟값 구하기
		OptionalInt min = intStream4.reduce(Integer::min);

		// 결과 출력
		System.out.println("count=" + count);
		System.out.println("sum=" + sum);
		System.out.println("max=" + max.getAsInt()); // OptionalInt 내부 값 추출
		System.out.println("min=" + min.getAsInt());
	}
}