import java.util.*;
import java.util.stream.*;

class StreamEx4 {
	public static void main(String[] args) {

		// =========================================================
		// 예제 1: String[] 배열 요소를 갖는 스트림을 평평하게(flat) 펼치기
		// =========================================================
		Stream<String[]> strArrStrm = Stream.of(
				new String[]{"abc", "def", "jkl"},
				new String[]{"ABC", "GHI", "JKL"}
		);

		// 만약 map(Arrays::stream)을 사용하면 Stream<Stream<String>> 형태의 2중 스트림이 됨
		// Stream<Stream<String>> strStrmStrm = strArrStrm.map(Arrays::stream);

		// flatMap을 사용하면 각 배열을 개별 스트림으로 변환한 후 하나로 합쳐서 Stream<String>으로 평탄화함
		Stream<String> strStrm = strArrStrm.flatMap(Arrays::stream);

		strStrm.map(String::toLowerCase)  // 모든 단어를 소문자로 변환 ("ABC" -> "abc")
				.distinct()                 // 중복 제거 ("abc", "jkl" 중복 제거)
				.sorted()                   // 사전순(오름차순) 정렬
				.forEach(System.out::println); // 결과 출력: abc, def, ghi, jkl

		System.out.println();

		// =========================================================
		// 예제 2: 문장(String) 배열을 공백 기준으로 단어별로 쪼개어 평탄화하기
		// =========================================================
		String[] lineArr = {
				"Believe or not It is true",
				"Do or do not There is no try",
		};

		Stream<String> lineStream = Arrays.stream(lineArr);

		// line.split(" +") : 공백(하나 이상의 연속된 공백 포함)을 기준으로 문장을 잘라 String[] 생성
		// Stream.of(...) : 자른 배열을 스트림으로 전환
		// flatMap : 생성된 각 단어 스트림들을 단일 Stream<String>으로 통합
		lineStream.flatMap(line -> Stream.of(line.split(" +")))
				.map(String::toLowerCase)  // 단어들을 모두 소문자로 일치
				.distinct()                 // 중복 단어 제거 ("or", "not", "is", "do" 등 중복 제거)
				.sorted()                   // 알파벳순 정렬
				.forEach(System.out::print); // 단어 출력 (줄바꿈 없이)

		System.out.println();
		System.out.println();

		// =========================================================
		// 예제 3: 두 개의 Stream<String>을 하나로 합치기
		// =========================================================
		Stream<String> strStrm1 = Stream.of("AAA", "ABC", "bBb", "Dd");
		Stream<String> strStrm2 = Stream.of("bbb", "aaa", "ccc", "dd");

		// 스트림 2개를 요소로 갖는 Stream<Stream<String>> 생성
		Stream<Stream<String>> strStrmStrm = Stream.of(strStrm1, strStrm2);

		// 1) map으로 각 Stream<String>을 String[] 배열로 변환
		// 2) flatMap(Arrays::stream)으로 배열들을 다시 단일 Stream<String>으로 평탄화
		Stream<String> strStream = strStrmStrm
				.map(s -> s.toArray(String[]::new))
				.flatMap(Arrays::stream);

		strStream.map(String::toLowerCase)  // 모두 소문자로 변환
				.distinct()                 // 중복 요소 제거
				.forEach(System.out::println); // 결과 출력
	}
}