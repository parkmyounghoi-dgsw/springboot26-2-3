package com.example.ex06.controller;

import com.example.ex06.entity.Member;
import com.example.ex06.repository.MemberRepository;
import com.example.ex06.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("members")
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {

    // 2가지...@Autowird private fianl
    private final MemberService memberService;

//    public MemberController(MemberService memberService) {
//        this.memberService = memberService;
//    }

    // members 호출 하게 되면...
//    @GetMapping("members")
    @GetMapping
    public List<Member> findAll() {
        return memberService.findAll();
    }

    @GetMapping("/{id}")
    public Member findById(@PathVariable Long id){
        System.out.println("id = "+id);
        return null;
    }

    // MemberController -> MemberSerivce -> MemberRepository ->Member
    // ProductConto
    // 장바구니
    //

    /*
        get방식.. member 테이블 전체조회...
        get방식.. member 테이블 행 한개씩 조회...
     */


}
