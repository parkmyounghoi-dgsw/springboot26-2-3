package hs.kr.pmh.suhang.controller;

import hs.kr.pmh.suhang.domain.Member;
import hs.kr.pmh.suhang.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        // given
        Member member = memberRepository.findById(1L).orElseThrow();

        // when
        ResultActions result = mockMvc.perform(get("/api/members"));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("성공"))
                .andExpect(jsonPath("$.data[0].name").value(member.getName()))
                .andExpect(jsonPath("$.data[0].email").value(member.getEmail()));
    }


}
