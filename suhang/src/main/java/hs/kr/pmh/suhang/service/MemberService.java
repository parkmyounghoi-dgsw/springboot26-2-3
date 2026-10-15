package hs.kr.pmh.suhang.service;

import hs.kr.pmh.suhang.domain.Member;

import java.util.List;

public interface MemberService {

    Member create(Member member);

    List<Member> findAll();

    Member findById(Long id);

    Member update(Long id, Member member);

    boolean delete(Long id);
}
