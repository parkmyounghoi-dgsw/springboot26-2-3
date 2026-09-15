package com.example.ex06.service;

import com.example.ex06.entity.Member;
import com.example.ex06.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findAll() {
        // select * from member;
        return memberRepository.findAll();
    }

    public Member findById(Long id) {
        // select * from member where id = ?id;
        Optional<Member> optionalMember =  memberRepository.findById(id);
        return optionalMember.get();
    }
}
