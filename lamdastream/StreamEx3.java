import java.util.*;
import java.util.stream.*;

class StreamEx3 {
	public static void main(String[] args) {
		// 1. 테스트용 학생(Student) 객체 배열 생성
		Student[] stuArr = {
				new Student("이자바", 3, 300),
				new Student("김자바", 1, 200),
				new Student("안자바", 2, 100),
				new Student("박자바", 2, 150),
				new Student("소자바", 1, 200),
				new Student("나자바", 3, 290),
				new Student("감자바", 3, 180)
		};

		// 2. Student 배열로부터 Stream<Student> 생성
		Stream<Student> stuStream = Stream.of(stuArr);

		// 1차: 반(ban) 기준 오름차순, 2차: 총점(totalScore) 기준 내림차순 정렬 후 출력
		stuStream.sorted(Comparator.comparing(Student::getBan)
						.thenComparing(Comparator.naturalOrder()))
				.forEach(System.out::println);

		// -------------------------------------------------------------
		// 최종 연산(forEach)이 실행되어 이전 스트림은 닫혔으므로 다시 생성
		stuStream = Stream.of(stuArr);

		// 3. mapToInt(): 참조형 Stream<Student>를 기본형 스트림인 IntStream으로 변환
		// (오토박싱/언박싱 오버헤드를 줄이고 정수 관련 통계 메서드를 직접 제공받기 위함)
		IntStream stuScoreStream = stuStream.mapToInt(Student::getTotalScore);

		// 4. summaryStatistics(): IntStream의 요소들에 대한 요약 통계 정보(개수, 합계, 평균, 최소, 최대)를
		// 한 번의 순회로 구하여 IntSummaryStatistics 객체로 반환
		IntSummaryStatistics stat = stuScoreStream.summaryStatistics();

		// 통계 결과 출력
		System.out.println("count=" + stat.getCount());           // 전체 학생 수 (long)
		System.out.println("sum=" + stat.getSum());               // 총점 합계 (long)
		System.out.printf("average=%.2f%n", stat.getAverage());  // 총점 평균 (double)
		System.out.println("min=" + stat.getMin());               // 최저 점수 (int)
		System.out.println("max=" + stat.getMax());               // 최고 점수 (int)
	}
}

// Student 클래스 (Comparable 인터페이스 구현)
class Student implements Comparable<Student> {
	String name;       // 학생 이름
	int ban;           // 반
	int totalScore;    // 총점

	// 생성자
	Student(String name, int ban, int totalScore) {
		this.name = name;
		this.ban  = ban;
		this.totalScore = totalScore;
	}

	// 객체 정보를 문자열로 반환
	@Override
	public String toString() {
		return String.format("[%s, %d, %d]", name, ban, totalScore);
	}

	// Getter 메서드들
	String getName()     { return name; }
	int getBan()         { return ban; }
	int getTotalScore()  { return totalScore; }

	// 기본 정렬 기준: 총점(totalScore) 내림차순 (높은 점수 우선)
	@Override
	public int compareTo(Student s) {
		return s.totalScore - this.totalScore;
	}
}