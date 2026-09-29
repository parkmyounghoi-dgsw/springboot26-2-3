package com.example.ex06.service;

import com.example.ex06.dto.MemberRequestDto;
import com.example.ex06.dto.MemberResponseDto;
import com.example.ex06.entity.Member;
import com.example.ex06.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    // 전체 조회
    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll().stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

    // 단건 조회
    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id=" + id));
        return new MemberResponseDto(member);
    }

    // 등록
    @Transactional
    public MemberResponseDto save(MemberRequestDto dto) {
        Member member = Member.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
        return new MemberResponseDto(memberRepository.save(member));
    }

    // PUT - 전체 수정
    @Transactional
    public MemberResponseDto update(Long id, MemberRequestDto dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id=" + id));
        member.update(dto.getName(), dto.getEmail());
        return new MemberResponseDto(member);
    }

    // PATCH - 부분 수정
    @Transactional
    public MemberResponseDto patch(Long id, MemberRequestDto dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id=" + id));
        member.patch(dto.getName(), dto.getEmail());
        return new MemberResponseDto(member);
    }

    // 삭제
    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id=" + id));
        memberRepository.delete(member);
    }
}
