import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

class Student {
	String name;
	boolean isMale; // 성별 (true: 남, false: 여)
	int hak;        // 학년
	int ban;        // 반
	int score;      // 점수

	Student(String name, boolean isMale, int hak, int ban, int score) {
		this.name   = name;
		this.isMale = isMale;
		this.hak    = hak;
		this.ban    = ban;
		this.score  = score;
	}

	String getName()   { return name; }
	boolean isMale()   { return isMale; }
	int getHak()       { return hak; }
	int getBan()       { return ban; }
	int getScore()     { return score; }

	@Override
	public String toString() {
		return String.format("[%s, %s, %d학년 %d반, %3d점]",
				name, isMale ? "남" : "여", hak, ban, score);
	}

	// 성적 등급을 나타내는 열거형
	enum Level {
		HIGH, MID, LOW
	}
}

class StreamEx8 {
	public static void main(String[] args) {
		// 테스트 데이터: 1~2학년, 1~3반, 남/여 학생 배열
		Student[] stuArr = {
				new Student("나자바", true,  1, 1, 300),
				new Student("김지미", false, 1, 1, 250),
				new Student("김자바", true,  1, 1, 200),
				new Student("이지미", false, 1, 2, 150),
				new Student("남자바", true,  1, 2, 100),
				new Student("안지미", false, 1, 2,  50),
				new Student("황지미", false, 1, 3, 100),
				new Student("강지미", false, 1, 3, 150),
				new Student("이자바", true,  1, 3, 200),

				new Student("나자바", true,  2, 1, 300),
				new Student("김지미", false, 2, 1, 250),
				new Student("김자바", true,  2, 1, 200),
				new Student("이지미", false, 2, 2, 150),
				new Student("남자바", true,  2, 2, 100),
				new Student("안지미", false, 2, 2,  50),
				new Student("황지미", false, 2, 3, 100),
				new Student("강지미", false, 2, 3, 150),
				new Student("이자바", true,  2, 3, 200)
		};

		// =========================================================
		// 1. 단순 그룹화: 반별 그룹화
		// =========================================================
		System.out.printf("1. 단순그룹화(반별 그룹화)%n");
		// Student::getBan을 기준으로 Map<반, List<Student>> 형태로 그룹화
		Map<Integer, List<Student>> stuByBan = Stream.of(stuArr)
				.collect(groupingBy(Student::getBan));

		for (List<Student> ban : stuByBan.values()) {
			for (Student s : ban) {
				System.out.println(s);
			}
		}

		// =========================================================
		// 2. 단순 그룹화: 성적 등급별 그룹화
		// =========================================================
		System.out.printf("%n2. 단순그룹화(성적등급별 그룹화)%n");
		// 람다식을 이용해 점수에 따라 HIGH, MID, LOW 등급으로 분류
		Map<Student.Level, List<Student>> stuByLevel = Stream.of(stuArr)
				.collect(groupingBy(s -> {
					if (s.getScore() >= 200)      return Student.Level.HIGH;
					else if (s.getScore() >= 100) return Student.Level.MID;
					else                          return Student.Level.LOW;
				}));

		// Enum 순서 또는 정렬을 위해 TreeSet 사용
		TreeSet<Student.Level> keySet = new TreeSet<>(stuByLevel.keySet());

		for (Student.Level key : keySet) {
			System.out.println("[" + key + "]");

			for (Student s : stuByLevel.get(key))
				System.out.println(s);
			System.out.println();
		}

		// =========================================================
		// 3. 단순 그룹화 + 하위 집계: 성적 등급별 학생 수 계산
		// =========================================================
		System.out.printf("%n3. 단순그룹화 + 통계(성적등급별 학생수)%n");
		// groupingBy의 두 번째 인자로 Downstream Collector인 counting()을 전달
		Map<Student.Level, Long> stuCntByLevel = Stream.of(stuArr)
				.collect(groupingBy(s -> {
					if (s.getScore() >= 200)      return Student.Level.HIGH;
					else if (s.getScore() >= 100) return Student.Level.MID;
					else                          return Student.Level.LOW;
				}, counting()));

		for (Student.Level key : stuCntByLevel.keySet())
			System.out.printf("[%s] - %d명, ", key, stuCntByLevel.get(key));
		System.out.println();

		// =========================================================
		// 4. 다중 그룹화: 학년별, 반별 중첩 그룹화
		// =========================================================
		System.out.printf("%n4. 다중그룹화(학년별, 반별)%n");
		// groupingBy 안에 groupingBy를 중첩하여 Map<학년, Map<반, List<Student>>> 생성
		Map<Integer, Map<Integer, List<Student>>> stuByHakAndBan =
				Stream.of(stuArr)
						.collect(groupingBy(Student::getHak,
								groupingBy(Student::getBan)
						));

		for (Map<Integer, List<Student>> hak : stuByHakAndBan.values()) {
			for (List<Student> ban : hak.values()) {
				System.out.println();
				for (Student s : ban)
					System.out.println(s);
			}
		}

		// =========================================================
		// 5. 다중 그룹화 + 하위 집계: 학년별, 반별 1등 학생 추출
		// =========================================================
		System.out.printf("%n5. 다중그룹화 + 통계(학년별, 반별 1등)%n");
		// maxBy는 Optional<Student>를 반환하므로,
		// collectingAndThen을 사용해 Optional에서 값(Student)만 뽑아 추출
		Map<Integer, Map<Integer, Student>> topStuByHakAndBan = Stream.of(stuArr)
				.collect(groupingBy(Student::getHak,
						groupingBy(Student::getBan,
								collectingAndThen(
										maxBy(comparingInt(Student::getScore)),
										Optional::get
								)
						)
				));

		for (Map<Integer, Student> ban : topStuByHakAndBan.values())
			for (Student s : ban.values())
				System.out.println(s);

		// =========================================================
		// 6. 다중 그룹화 + 하위 변환: 학년-반별 성적등급 세트(Set) 추출
		// =========================================================
		System.out.printf("%n6. 다중그룹화 + 통계(학년별, 반별 성적그룹)%n");
		// mapping()을 사용해 Student 객체를 Level 등급으로 변환한 뒤 Set으로 수집 (중복 제거)
		Map<String, Set<Student.Level>> stuByScoreGroup = Stream.of(stuArr)
				.collect(groupingBy(s -> s.getHak() + "-" + s.getBan(),
						mapping(s -> {
							if (s.getScore() >= 200)      return Student.Level.HIGH;
							else if (s.getScore() >= 100) return Student.Level.MID;
							else                          return Student.Level.LOW;
						}, toSet())
				));

		Set<String> keySet2 = stuByScoreGroup.keySet();

		for (String key : keySet2) {
			System.out.println("[" + key + "]" + stuByScoreGroup.get(key));
		}
	} // main 끝
}