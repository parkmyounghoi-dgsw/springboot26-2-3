package com.example.ex06.service;

import com.example.ex06.dto.MemberRequestDto;
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

    public Member save(MemberRequestDto memberRequestDto) {
//        Member member = new Member();
        Member member = Member
                        .builder()
                        .name(memberRequestDto.getName())
                        .email(memberRequestDto.getEmail())
                        .build();

        // insert into member values();
        return memberRepository.save(member);
    }

    public Member update(Long id,Member member) throws Exception {
        // select * from member where id = ?
        // 해당하는 행이 있는지 조회...
        Optional<Member> memberOptional = memberRepository.findById(id);
        // 해당하는 행이 있으면.. update 구문 실행
        if(memberOptional.isPresent()){
            Member temp = memberOptional.get();
            temp.setEmail(member.getEmail());
            temp.setName(member.getName());
            // update 구문을 실행을 해달라..
            return memberRepository.save(temp);
        }else{
            throw new Exception("해당하는 id "+id+"가 없어서 수정할 내용이 없습니다.");
        }
    }

    public boolean deleteMember(Long id) throws Exception {
        // select * from member where id = ?
        Member member = memberRepository
                .findById(id)
                .orElseThrow(() -> new Exception("해당하는 id "+id+"가 없어서 삭제할 내용이 없습니다."));
        memberRepository.delete(member);
        return true;
    }
}
