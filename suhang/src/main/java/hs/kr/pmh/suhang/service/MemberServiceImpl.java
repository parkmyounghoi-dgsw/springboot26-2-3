package hs.kr.pmh.suhang.service;

import hs.kr.pmh.suhang.domain.Member;
import hs.kr.pmh.suhang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

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

}
