package kr.hs.dgsw.evaluation.repository;

import kr.hs.dgsw.evaluation.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
