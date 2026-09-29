package com.example.ex06.controller;

import com.example.ex06.dto.MemberResponseDto;
import com.example.ex06.entity.Member;
import com.example.ex06.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    private MemberResponseDto mockResponse(Long id, String name, String email) {
        Member member = Member.builder()
                .id(id).name(name).email(email).build();
        return new MemberResponseDto(member);
    }

    @Test
    @DisplayName("GET /api/members - 전체 조회")
    void findAll() throws Exception {
        given(memberService.findAll()).willReturn(List.of(
                mockResponse(1L, "홍길동", "hong@example.com"),
                mockResponse(2L, "김철수", "kim@example.com")
        ));

        mockMvc.perform(get("/api/members"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("홍길동"))
                .andExpect(jsonPath("$[1].name").value("김철수"));
    }

    @Test
    @DisplayName("GET /api/members/{id} - 단건 조회")
    void findById() throws Exception {
        given(memberService.findById(1L)).willReturn(mockResponse(1L, "홍길동", "hong@example.com"));

        mockMvc.perform(get("/api/members/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("홍길동"))
                .andExpect(jsonPath("$.email").value("hong@example.com"));
    }

    @Test
    @DisplayName("POST /api/members - 등록")
    void save() throws Exception {
        given(memberService.save(any())).willReturn(mockResponse(3L, "이영희", "lee@example.com"));

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": "이영희", "email": "lee@example.com"}
                                """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("이영희"));
    }

    @Test
    @DisplayName("PUT /api/members/{id} - 전체 수정")
    void update() throws Exception {
        given(memberService.update(eq(1L), any())).willReturn(mockResponse(1L, "홍길순", "new@example.com"));

        mockMvc.perform(put("/api/members/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": "홍길순", "email": "new@example.com"}
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("홍길순"))
                .andExpect(jsonPath("$.email").value("new@example.com"));
    }

    @Test
    @DisplayName("PATCH /api/members/{id} - 부분 수정")
    void patchMember() throws Exception {
        given(memberService.patch(eq(1L), any())).willReturn(mockResponse(1L, "홍길순", "hong@example.com"));

        mockMvc.perform(patch("/api/members/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": "홍길순"}
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("홍길순"))
                .andExpect(jsonPath("$.email").value("hong@example.com"));
    }

    @Test
    @DisplayName("DELETE /api/members/{id} - 삭제")
    void deleteMember() throws Exception {
        willDoNothing().given(memberService).delete(1L);

        mockMvc.perform(delete("/api/members/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
