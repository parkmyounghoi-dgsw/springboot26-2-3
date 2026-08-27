import java.io.*;
import java.util.stream.*;

class StreamEx2 {
	public static void main(String[] args) {
		// 1. 테스트용 File 객체 배열 생성 (확장자가 있는 파일, 없는 파일 섞임)
		File[] fileArr = {
				new File("Ex1.java"),
				new File("Ex1.bak"),
				new File("Ex2.java"),
				new File("Ex1"),       // 확장자가 없는 파일
				new File("Ex1.txt")
		};

		// 2. File 배열로부터 Stream<File> 생성
		Stream<File> fileStream = Stream.of(fileArr);

		// map()을 이용해 Stream<File>을 파일명인 Stream<String>으로 변환
		// File::getName 메서드 참조 사용 (f -> f.getName()과 동일)
		Stream<String> filenameStream = fileStream.map(File::getName);

		// 최종 연산: 모든 파일의 이름을 콘솔에 한 줄씩 출력
		filenameStream.forEach(System.out::println);

		// -------------------------------------------------------------
		// 스트림은 일회성이므로, 한번 최종 연산(forEach)을 수행한 스트림은 재사용 불가!
		// 따라서 새로운 처리를 위해 스트림을 다시 생성함
		fileStream = Stream.of(fileArr);

		// 3. 파이프라인 연산을 통한 확장자 추출 및 중복 제거 후 출력
		fileStream.map(File::getName)                    // Stream<File> -> Stream<String> (파일명만 추출)
				.filter(s -> s.indexOf('.') != -1)     // 필터링: 파일명에 점('.')이 포함된 파일만 통과 (확장자 존재 여부)
				.map(s -> s.substring(s.indexOf('.') + 1)) // 변환: 점('.') 다음 문자열부터 잘라내어 확장자만 추출
				.map(String::toUpperCase)              // 변환: 모든 확장자를 대문자로 변환 (예: java -> JAVA)
				.distinct()                            // 중복 제거: 중복되는 확장자 제거 (예: JAVA가 2개라면 1개만 남김)
				.forEach(System.out::print);           // 최종 연산: 결과를 줄바꿈 없이 이어 붙여서 출력 -> JAVABAKTXT

		System.out.println(); // 줄바꿈용 출력
	}
}