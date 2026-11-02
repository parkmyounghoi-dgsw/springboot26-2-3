package kr.hs.dgsw.evaluation.controller;

import kr.hs.dgsw.evaluation.domain.Member;
import kr.hs.dgsw.evaluation.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public Map<String, Object> create(@RequestBody Member member) {
        Member created = memberService.create(member);
        return created == null
                ? Map.of("result", "실패", "message", "이름과 이메일은 필수입니다.")
                : Map.of("result", "성공", "data", created);
//        Map<String, Object> response = new HashMap<>();
//        if (created == null) {
//            response.put("result", "실패");
//            response.put("message", "이름과 이메일은 필수입니다.");
//        } else {
//            response.put("result", "성공");
//            response.put("data", created);
//        }
    }

    @GetMapping
    public Map<String, Object> findAll() {
        List<Member> members = memberService.findAll();
        return Map.of("result", "성공", "data", members);
    }

    @GetMapping("/{id}")
    public Map<String, Object> findById(@PathVariable Long id) {
        Member member = memberService.findById(id);
        return member == null
                ? Map.of("result", "실패", "message", "회원을 찾을 수 없습니다.")
                : Map.of("result", "성공", "data", member);
    }

    @PutMapping("/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody Member member) {
        Member updated = memberService.update(id, member);
        return updated == null
                ? Map.of("result", "실패", "message", "회원 정보를 찾을 수 없거나 이름 또는 이메일이 입력되지 않았습니다.")
                : Map.of("result", "성공", "data", updated);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        return memberService.delete(id)
                ? Map.of("result", "성공")
                : Map.of("result", "실패", "message", "회원을 찾을 수 없습니다.");
    }


}
