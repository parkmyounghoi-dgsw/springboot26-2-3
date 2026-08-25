package com.dgsw.org.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Members", description = "회원 CRUD API")
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "회원 목록 조회")
    @GetMapping
    public List<MemberResponse> findAll() {
        return memberService.findAll();
    }

    @Operation(summary = "회원 단건 조회")
    @GetMapping("/{id}")
    public MemberResponse findById(@PathVariable Long id) {
        return memberService.findById(id);
    }

    @Operation(summary = "회원 생성")
    @PostMapping
    public ResponseEntity<MemberResponse> create(@Valid @RequestBody MemberRequest.CreateOrUpdate request) {
        MemberResponse response = memberService.create(request);
        return ResponseEntity.created(URI.create("/api/members/" + response.id())).body(response);
    }

    @Operation(summary = "회원 전체 수정")
    @PutMapping("/{id}")
    public MemberResponse update(@PathVariable Long id, @Valid @RequestBody MemberRequest.CreateOrUpdate request) {
        return memberService.update(id, request);
    }

    @Operation(summary = "회원 일부 수정")
    @PatchMapping("/{id}")
    public MemberResponse patch(@PathVariable Long id, @Valid @RequestBody MemberRequest.Patch request) {
        return memberService.patch(id, request);
    }

    @Operation(summary = "회원 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
