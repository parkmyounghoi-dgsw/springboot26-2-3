package com.dgsw.org.member;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberResponse> findAll() {
        return memberRepository.findAll().stream().map(MemberResponse::from).toList();
    }

    public MemberResponse findById(Long id) {
        return MemberResponse.from(findMember(id));
    }

    @Transactional
    public MemberResponse create(MemberRequest.CreateOrUpdate request) {
        return MemberResponse.from(memberRepository.save(new Member(request.name())));
    }

    @Transactional
    public MemberResponse update(Long id, MemberRequest.CreateOrUpdate request) {
        Member member = findMember(id);
        member.changeName(request.name());
        return MemberResponse.from(member);
    }

    @Transactional
    public MemberResponse patch(Long id, MemberRequest.Patch request) {
        Member member = findMember(id);
        if (request.name() != null) {
            if (request.name().isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "name은 비어 있을 수 없습니다.");
            }
            member.changeName(request.name());
        }
        return MemberResponse.from(member);
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.delete(findMember(id));
    }

    private Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다. id=" + id));
    }
}
