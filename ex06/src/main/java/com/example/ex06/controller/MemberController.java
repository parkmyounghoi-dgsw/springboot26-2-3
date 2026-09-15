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
@CrossOrigin // Cors 에러 해결입니다...
public class MemberController {

    // 2가지...@Autowird private fianl
    private final MemberService memberService;

//    public MemberController(MemberService memberService) {
//        this.memberService = memberService;
//    }


    /*
        ProductController - ProductService - ProductRepository - Product(Entity)
        getMapping(products) -> product테이블 전체 내용 가져오기
        getMapping({id}) -> product테이블에서 한개 가져오기
     */

    // members 호출 하게 되면...
//    @GetMapping("members")
    @GetMapping
    public List<Member> findAll() {
        return memberService.findAll();
    }

    @GetMapping("/{id}")
    public Member findById(@PathVariable Long id){
        System.out.println("id = "+id);
        Member member = memberService.findById(id);
        return member;
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
