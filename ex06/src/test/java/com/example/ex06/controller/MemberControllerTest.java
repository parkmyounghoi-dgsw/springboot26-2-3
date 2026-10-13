package com.example.ex06.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll() throws Exception{
        //when
        ResultActions resultActions = mockMvc.perform(get("/members"));
        // then
        resultActions.andDo(print())
                .andExpect(jsonPath("$[0].name").value("박길동"))
                .andExpect(jsonPath("$[0].email").value("aaa@naver.com"));
    }

    @Test
    void findById() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/members/2"));

            resultActions.andDo(print())
                    .andExpect(jsonPath("$.name").value("박길동"))
                    .andExpect(jsonPath("$.email").value("aaa@naver.com"));
    }

    @Test
    void save() throws Exception {
        ResultActions resultActions =
                    mockMvc.perform(post("/members"));
        resultActions.andDo(print());
    }

    @Test
    void update() {
    }

    @Test
    void deleteMember() {
    }
}