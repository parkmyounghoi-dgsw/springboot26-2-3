package kr.hs.dgsw.evaluation.service;

import kr.hs.dgsw.evaluation.domain.Member;

import java.util.List;

public interface MemberService {

    Member create(Member member);

    List<Member> findAll();

    Member findById(Long id);

    Member update(Long id, Member member);

    boolean delete(Long id);
}
