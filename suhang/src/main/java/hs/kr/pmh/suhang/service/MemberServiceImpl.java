package hs.kr.pmh.suhang.service;

import hs.kr.pmh.suhang.domain.Member;
import hs.kr.pmh.suhang.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
//    @Autowired
//    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Member create(Member member) {
        if (!StringUtils.hasText(member.getName()) || !StringUtils.hasText(member.getEmail())) {
            return null;
        }
        return memberRepository.save(member);
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    @Override
    public Member update(Long id, Member member) {
        Member existing = memberRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        } else if (!StringUtils.hasText(member.getName())) {
            return null;
        } else if (!StringUtils.hasText(member.getEmail())) {
            return null;
        }
        existing.update(member.getName(), member.getEmail());
        return memberRepository.save(existing);
    }

    @Override
    public boolean delete(Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // entityManager.contains
    // 쓰기 지연
    @Transactional
    public void firstCache(){
        entityManager.persist(new Member("쓰기 지연1","abc1@naver.com"));
        entityManager.persist(new Member("쓰기 지연2","abc2@naver.com"));

        System.out.println("insert 시작 ");
        entityManager.flush();
        System.out.println("insert 끝");

        throw new RuntimeException("test");
    }
}
