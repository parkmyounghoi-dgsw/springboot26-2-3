package hs.kr.pmh.suhang.repository;

import hs.kr.pmh.suhang.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
