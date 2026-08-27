import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.*; // Collectors 클래스의 정적 메서드들을 클래스명 없이 직접 호출 가능하게 함

class StreamEx6 {
	public static void main(String[] args) {
		// 1. 테스트용 Student 객체 배열 생성
		Student[] stuArr = {
				new Student("이자바", 3, 300),
				new Student("김자바", 1, 200),
				new Student("안자바", 2, 100),
				new Student("박자바", 2, 150),
				new Student("소자바", 1, 200),
				new Student("나자바", 3, 290),
				new Student("감자바", 3, 180)
		};

		// 2. collect(toList()): 학생 이름만 추출하여 List<String> 컬렉션으로 수집
		List<String> names = Stream.of(stuArr)
				.map(Student::getName)
				.collect(Collectors.toList());
		System.out.println(names);

		// 3. toArray(): 스트림의 요소를 배열 형태로 반환
		// 생성자 참조(Student[]::new)를 전달하여 적절한 타입의 배열을 생성
		Student[] stuArr2 = Stream.of(stuArr).toArray(Student[]::new);

		for (Student s : stuArr2)
			System.out.println(s);

		// 4. collect(toMap()): 스트림의 요소를 Map 형태로 변환
		// Key: 학생 이름 (s -> s.getName()), Value: Student 객체 자신 (p -> p 또는 Function.identity())
		// 주의: 학생 이름(Key)이 중복될 경우 IllegalStateException 발생함
		Map<String, Student> stuMap = Stream.of(stuArr)
				.collect(Collectors.toMap(s -> s.getName(), p -> p));
		for (String name : stuMap.keySet())
			System.out.println(name + "-" + stuMap.get(name));

		// 5. collect(counting()) / collect(summingInt()): 요소 개수 수집 및 총점 합계
		long count = Stream.of(stuArr).collect(counting());
		long totalScore = Stream.of(stuArr)
				.collect(summingInt(Student::getTotalScore));
		System.out.println("count=" + count);
		System.out.println("totalScore=" + totalScore);

		// 6. collect(reducing()): 리듀싱 작업을 Collector로 수행
		// 초기값(0), 매핑 함수(Student::getTotalScore), 결합 함수(Integer::sum)
		totalScore = Stream.of(stuArr)
				.collect(reducing(0, Student::getTotalScore, Integer::sum));
		System.out.println("totalScore=" + totalScore);

		// 7. collect(maxBy()): 특정 Comparator 기준에 따른 최댓값을 구해 Optional<T>로 반환
		Optional<Student> topStudent = Stream.of(stuArr)
				.collect(maxBy(Comparator.comparingInt(Student::getTotalScore)));
		System.out.println("topStudent=" + topStudent.get());

		// 8. collect(summarizingInt()): 개수, 합계, 평균, 최댓값, 최솟값을 모두 포함하는 통계 객체 반환
		IntSummaryStatistics stat = Stream.of(stuArr)
				.collect(summarizingInt(Student::getTotalScore));
		System.out.println(stat);

		// 9. collect(joining()): 문자열 요소들을 연결
		// 구분자(","), 접두사("{"), 접미사("}")를 지정하여 결합 -> "{이자바,김자바,안자바,...}"
		String stuNames = Stream.of(stuArr)
				.map(Student::getName)
				.collect(joining(",", "{", "}"));
		System.out.println(stuNames);
	}
}

// Student 클래스
class Student implements Comparable<Student> {
	String name;       // 이름
	int ban;           // 반
	int totalScore;    // 총점

	Student(String name, int ban, int totalScore) {
		this.name = name;
		this.ban = ban;
		this.totalScore = totalScore;
	}

	@Override
	public String toString() {
		return String.format("[%s, %d, %d]", name, ban, totalScore);
	}

	String getName()     { return name; }
	int getBan()         { return ban; }
	int getTotalScore()  { return totalScore; }

	@Override
	public int compareTo(Student s) {
		return s.totalScore - this.totalScore; // 총점 내림차순 정렬
	}
}