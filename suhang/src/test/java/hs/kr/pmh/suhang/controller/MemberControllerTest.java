package hs.kr.pmh.suhang.controller;

import hs.kr.pmh.suhang.domain.Member;
import hs.kr.pmh.suhang.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // GET /api/members - 전체 조회
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

    // GET /api/members/{id} - 단건 조회
    @Test
    void findById() throws Exception {
        // given
        Member member = memberRepository.findById(1L).orElseThrow();

        // when
        ResultActions result = mockMvc.perform(get("/api/members/{id}", 1L));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("성공"))
                .andExpect(jsonPath("$.data.name").value(member.getName()))
                .andExpect(jsonPath("$.data.email").value(member.getEmail()));
    }

    // GET /api/members/{id} - 존재하지 않는 id 조회 시 404
    @Test
    void findById_notFound() throws Exception {
        // when
        ResultActions result = mockMvc.perform(get("/api/members/{id}", 9999L));

        // then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.result").value("실패"))
                .andExpect(jsonPath("$.message").value("회원을 찾을 수 없습니다."));
    }

    // POST /api/members - 생성
    @Test
    void create() throws Exception {
        // given
        Member newMember = new Member("홍길동", "hong@test.com");

        // when
        ResultActions result = mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newMember)));

        // then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.result").value("성공"))
                .andExpect(jsonPath("$.data.name").value("홍길동"))
                .andExpect(jsonPath("$.data.email").value("hong@test.com"));
    }

    // POST /api/members - 이름 없이 생성 시 400
    @Test
    void create_fail_missingName() throws Exception {
        // given
        Member invalidMember = new Member("", "noname@test.com");

        // when
        ResultActions result = mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidMember)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value("실패"))
                .andExpect(jsonPath("$.message").value("이름과 이메일은 필수입니다."));
    }

    // PUT /api/members/{id} - 수정
    @Test
    void update() throws Exception {
        // given
        Member member = memberRepository.findById(1L).orElseThrow();
        Member updateRequest = new Member("수정된이름", "updated@test.com");

        // when
        ResultActions result = mockMvc.perform(put("/api/members/{id}", member.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("성공"))
                .andExpect(jsonPath("$.data.name").value("수정된이름"))
                .andExpect(jsonPath("$.data.email").value("updated@test.com"));
    }

    // PUT /api/members/{id} - 존재하지 않는 id 수정 시 400
    @Test
    void update_notFound() throws Exception {
        // given
        Member updateRequest = new Member("수정된이름", "updated@test.com");

        // when
        ResultActions result = mockMvc.perform(put("/api/members/{id}", 9999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value("실패"))
                .andExpect(jsonPath("$.message").value("회원 정보를 찾을 수 없거나 이름 또는 이메일이 입력되지 않았습니다."));
    }

    // DELETE /api/members/{id} - 삭제
    @Test
    void deleteById() throws Exception {
        // given
        Member saved = memberRepository.save(new Member("삭제대상", "delete@test.com"));

        // when
        ResultActions result = mockMvc.perform(delete("/api/members/{id}", saved.getId()));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("성공"));
    }

    // DELETE /api/members/{id} - 존재하지 않는 id 삭제 시 404
    @Test
    void deleteById_notFound() throws Exception {
        // when
        ResultActions result = mockMvc.perform(delete("/api/members/{id}", 9999L));

        // then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.result").value("실패"))
                .andExpect(jsonPath("$.message").value("회원을 찾을 수 없습니다."));
    }
}
