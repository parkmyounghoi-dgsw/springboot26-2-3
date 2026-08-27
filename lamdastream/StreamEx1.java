import java.util.*;
import java.util.stream.*;

class StreamEx1 {
	public static void main(String[] args) {
		// Student 객체들을 요소로 갖는 스트림(Stream) 생성
		// (가상의 학생 이름, 반, 총점 데이터)
		Stream<Student> studentStream = Stream.of(
				new Student("이자바", 3, 300),
				new Student("김자바", 1, 200),
				new Student("안자바", 2, 100),
				new Student("박자바", 2, 150),
				new Student("소자바", 1, 200),
				new Student("나자바", 3, 290),
				new Student("감자바", 3, 180)
		);

		// 스트림 정렬 및 출력 (다중 조건 정렬)
		studentStream
				// 1차 정렬 기준: 반(ban) 기준 오름차순 정렬 (1반 -> 2반 -> 3반)
				.sorted(Comparator.comparing(Student::getBan)
						// 2차 정렬 기준: 반이 같을 경우 Student 클래스에 구현된 기본 정렬 방식(naturalOrder) 적용
						// Student의 compareTo 메서드에 의해 '총점(totalScore) 내림차순'으로 정렬됨
						.thenComparing(Comparator.naturalOrder()))
				// 최종 연산: 정렬된 결과를 하나씩 순회하며 콘솔에 출력
				.forEach(System.out::println);
	}
}

// Comparable 인터페이스를 구현하여 Student 객체 자체의 '기본 정렬 기준'을 정의
class Student implements Comparable<Student> {
	String name;       // 이름
	int ban;           // 반
	int totalScore;    // 총점

	// 생성자: 학생 객체 초기화
	Student(String name, int ban, int totalScore) {
		this.name = name;
		this.ban = ban;
		this.totalScore = totalScore;
	}

	// 객체 정보를 [이름, 반, 총점] 포맷의 문자열로 반환 (출력용)
	@Override
	public String toString() {
		return String.format("[%s, %d, %d]", name, ban, totalScore);
	}

	// Getter 메서드들 (Stream의 메서드 참조에서 사용됨)
	String getName()     { return name; }
	int getBan()         { return ban; }
	int getTotalScore()  { return totalScore; }

	/**
	 * Comparable 인터페이스의 compareTo 메서드 오버라이딩
	 * 총점(totalScore)을 기준으로 '내림차순(높은 점수 우선)' 정렬하도록 정의함
	 *
	 * - 반환값이 양수: 이 객체(this)가 인자로 들어온 객체(s)보다 뒤에 위치
	 * - 반환값이 0: 두 객체의 순위가 같음
	 * - 반환값이 음수: 이 객체(this)가 인자로 들어온 객체(s)보다 앞에 위치
	 */
	@Override
	public int compareTo(Student s) {
		return s.totalScore - this.totalScore; // (타객체 점수 - 내 점수) -> 내림차순 정렬
	}
}