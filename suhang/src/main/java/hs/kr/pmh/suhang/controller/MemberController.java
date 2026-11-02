package hs.kr.pmh.suhang.controller;

import hs.kr.pmh.suhang.domain.Member;
import hs.kr.pmh.suhang.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Tag(name = "Member", description = "회원 관리 API")
public class MemberController {

    private final MemberService memberService;

    // EntityManager는 @RequiredArgsConstructor가 아닌 @PersistenceContext로 주입
    @PersistenceContext
    private EntityManager em;

    // POST /api/members
    // 요청 Body로 받은 회원 정보(이름, 이메일)를 저장하고, 저장된 회원 데이터를 반환한다.
    @Operation(summary = "회원 등록", description = "이름과 이메일을 입력받아 새 회원을 등록합니다.")
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Member member) {
        Member created = memberService.create(member);
        if (created == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("result", "실패", "message", "이름과 이메일은 필수입니다."));
        } else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("result", "성공", "data", created));
        }
    }

    // GET /api/members
    // 저장된 모든 회원 목록을 조회하여 반환한다.
    @Operation(summary = "전체 회원 조회", description = "저장된 모든 회원 목록을 반환합니다.")
    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll() {
        List<Member> members = memberService.findAll();
        return ResponseEntity.ok(Map.of("result", "성공", "data", members));
    }

    // GET /api/members/{id}
    // 경로 변수로 받은 ID에 해당하는 회원 정보를 조회하여 반환한다.
    @Operation(summary = "회원 단건 조회", description = "ID로 특정 회원 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        Member member = memberService.findById(id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("result", "실패", "message", "회원을 찾을 수 없습니다."));
        } else {
            return ResponseEntity.ok(Map.of("result", "성공", "data", member));
        }
    }

    // PUT /api/members/{id}
    // 경로 변수로 받은 ID에 해당하는 회원의 이름과 이메일을 수정하고, 수정된 회원 데이터를 반환한다.
    @Operation(summary = "회원 정보 수정", description = "ID로 특정 회원의 이름과 이메일을 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody Member member) {
        Member updated = memberService.update(id, member);
        if (updated == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("result", "실패", "message", "회원 정보를 찾을 수 없거나 이름 또는 이메일이 입력되지 않았습니다."));
        } else {
            return ResponseEntity.ok(Map.of("result", "성공", "data", updated));
        }
    }

    // DELETE /api/members/{id}
    // 경로 변수로 받은 ID에 해당하는 회원을 삭제하고, 성공 여부를 반환한다.
    @Operation(summary = "회원 삭제", description = "ID로 특정 회원을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        if (memberService.delete(id)) {
            return ResponseEntity.ok(Map.of("result", "성공"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("result", "실패", "message", "회원을 찾을 수 없습니다."));
        }
    }

    // ──────────────────────────────────────────────────────────────────────
    //  JPA 핵심 개념 데모 엔드포인트
    // ──────────────────────────────────────────────────────────────────────

    /**
     * 1. 1차 캐시 (First-Level Cache)
     * - 같은 트랜잭션 안에서 동일 ID로 em.find()를 두 번 호출하면
     *   두 번째 조회는 DB를 거치지 않고 영속성 컨텍스트(1차 캐시)에서 반환된다.
     * - 로그에서 SELECT 쿼리가 한 번만 출력되는 것으로 확인 가능.
     */
    @Operation(summary = "[JPA] 1차 캐시 데모",
               description = "같은 ID로 em.find()를 두 번 호출 → 두 번째는 DB 조회 없이 캐시에서 반환됩니다.")
    @GetMapping("/jpa/first-level-cache/{id}")
    @Transactional
    public ResponseEntity<Map<String, Object>> firstLevelCacheDemo(@PathVariable Long id) {
        // 첫 번째 조회: DB SELECT 발생
        Member member1 = em.find(Member.class, id);
        if (member1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("result", "실패", "message", "회원을 찾을 수 없습니다."));
        }

        // 두 번째 조회: 1차 캐시에서 반환 (DB SELECT 없음 → 로그에 쿼리 미출력)
        Member member2 = em.find(Member.class, id);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("result", "성공");
        result.put("설명", "두 번 find() 호출 → SQL 은 한 번만 실행됩니다 (콘솔 로그 확인)");
        result.put("같은 인스턴스?", member1 == member2);   // true: 동일 객체
        result.put("data", member1);
        return ResponseEntity.ok(result);
    }

    /**
     * 2. 쓰기 지연 (Write-Behind / Transactional Write-Behind)
     * - em.persist()를 호출해도 즉시 INSERT가 나가지 않고,
     *   트랜잭션 커밋(또는 flush) 시점에 한꺼번에 SQL이 실행된다.
     * - 로그에서 persist 직후가 아닌 메서드 종료(커밋) 직전에 INSERT가 출력됨을 확인 가능.
     */
    @Operation(summary = "[JPA] 쓰기 지연 데모",
               description = "em.persist() 후 즉시 INSERT가 아닌, 트랜잭션 커밋 시점에 SQL이 실행됩니다.")
    @GetMapping("/jpa/write-behind")
    @Transactional
    public ResponseEntity<Map<String, Object>> writeBehindDemo() {
        Member newMember1 = new Member("쓰기지연테스트11", "writebehind1@test.com");
        Member newMember2 = new Member("쓰기지연테스트22", "writebehind2@test.com");
        Member newMember3= new Member("쓰기지연테스트33", "writebehind3@test.com");

        em.persist(newMember1);  // 아직 INSERT SQL 미실행 → 쓰기 지연 SQL 저장소에 적재
        em.persist(newMember2);  // 아직 INSERT SQL 미실행 → 쓰기 지연 SQL 저장소에 적재
        em.persist(newMember3);  // 아직 INSERT SQL 미실행 → 쓰기 지연 SQL 저장소에 적재

        System.out.println("쓰기지연 테스트 하기");
        em.flush();

        // persist 직후에는 DB에 INSERT가 나가지 않았지만,
        // newMember.getId()는 IDENTITY 전략이라 이 시점에 INSERT가 실행되어 id가 채워짐.
        // (IDENTITY 전략은 id 확보를 위해 persist 시점에 즉시 INSERT 실행이 예외적으로 발생)
        // → SEQUENCE / TABLE 전략일 때 쓰기 지연의 효과가 더 잘 드러남.

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("result", "성공");
        result.put("설명", "persist() 후 트랜잭션 커밋 시점에 INSERT SQL이 실행됩니다 (콘솔 로그 확인)");
        return ResponseEntity.ok(result);
    }

    /**
     * 3. 변경 감지 (Dirty Checking)
     * - 영속 상태의 엔티티를 수정하면 별도의 em.update() 없이도
     *   트랜잭션 커밋 시점에 JPA가 스냅샷과 비교해 자동으로 UPDATE SQL을 생성한다.
     */
    @Operation(summary = "[JPA] 변경 감지 데모",
               description = "em.find()로 조회 후 setter 없이 update() 호출만 해도 트랜잭션 종료 시 UPDATE SQL이 자동 실행됩니다.")
    @GetMapping("/jpa/dirty-checking/{id}")
    @Transactional
    public ResponseEntity<Map<String, Object>> dirtyCheckingDemo(@PathVariable Long id) {
        Member member = em.find(Member.class, id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("result", "실패", "message", "회원을 찾을 수 없습니다."));
        }

        String beforeName = member.getName();

        // em.merge() / em.update() 호출 없이 엔티티 상태만 변경
        // → 트랜잭션 커밋 시 JPA가 스냅샷 비교 후 UPDATE SQL 자동 실행
        member.update("[변경감지]" + beforeName, member.getEmail());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("result", "성공");
        result.put("설명", "update() 메서드만 호출했지만, 커밋 시 UPDATE SQL이 자동 실행됩니다 (콘솔 로그 확인)");
        result.put("변경 전 이름", beforeName);
        result.put("변경 후 이름", member.getName());
        return ResponseEntity.ok(result);
    }

    /**
     * 4. flush 실패 (Flush Failure / Constraint Violation)
     * - 영속성 컨텍스트를 강제로 flush할 때 DB 제약 조건을 위반하면
     *   flush 시점에 예외가 발생하며 트랜잭션이 롤백된다.
     * - unique 제약이 걸린 email에 이미 존재하는 값을 넣고 flush를 강제 호출하여 재현한다.
     */
    @Operation(summary = "[JPA] flush 실패 데모",
               description = "중복 email로 persist 후 em.flush()를 강제 호출 → DB 제약 위반으로 flush 시점에 예외가 발생합니다.")
    @GetMapping("/jpa/flush-fail/{id}")
    @Transactional
    public ResponseEntity<Map<String, Object>> flushFailDemo(@PathVariable Long id) {
        Member existing = em.find(Member.class, id);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("result", "실패", "message", "기준으로 삼을 회원을 찾을 수 없습니다."));
        }

        // 이미 존재하는 email과 동일한 값으로 새 회원 생성 → unique 제약 위반 예정
        Member duplicate = new Member("중복회원", existing.getEmail());
        em.persist(duplicate);

        try {
            // flush를 강제 호출 → INSERT 시도 → DB unique 제약 위반 예외 발생
            em.flush();

            // 여기까지 실행되면 flush 성공 (테스트 목적상 도달하지 않아야 정상)
            return ResponseEntity.ok(Map.of("result", "flush 성공 (예상치 못한 결과)"));
        } catch (Exception e) {
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("result", "실패 (정상 동작)");
            result.put("설명", "flush 시점에 unique 제약 위반으로 예외가 발생했습니다.");
            result.put("예외 클래스", e.getClass().getSimpleName());
            result.put("예외 메시지", e.getMessage() != null ? e.getMessage().split("\n")[0] : "없음");
            result.put("중복된 email", existing.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
    }
}
