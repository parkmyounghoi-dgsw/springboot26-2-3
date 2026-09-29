package com.example.ex06.controller;

import com.example.ex06.dto.MemberRequestDto;
import com.example.ex06.dto.MemberResponseDto;
import com.example.ex06.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // GET /api/members - 전체 조회
    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> findAll() {
        return ResponseEntity.ok(memberService.findAll());
    }

    // GET /api/members/{id} - 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.findById(id));
    }

    // POST /api/members - 등록
    @PostMapping
    public ResponseEntity<MemberResponseDto> save(@RequestBody MemberRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.save(dto));
    }

    // PUT /api/members/{id} - 전체 수정
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDto> update(@PathVariable Long id,
                                                     @RequestBody MemberRequestDto dto) {
        return ResponseEntity.ok(memberService.update(id, dto));
    }

    // PATCH /api/members/{id} - 부분 수정
    @PatchMapping("/{id}")
    public ResponseEntity<MemberResponseDto> patch(@PathVariable Long id,
                                                    @RequestBody MemberRequestDto dto) {
        return ResponseEntity.ok(memberService.patch(id, dto));
    }

    // DELETE /api/members/{id} - 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
