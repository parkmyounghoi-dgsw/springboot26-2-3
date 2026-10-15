package hs.kr.pmh.suhang.controller;

import hs.kr.pmh.suhang.domain.Product;
import hs.kr.pmh.suhang.repository.ProductRepository;
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
class ProductControllerTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // GET /api/products - 전체 조회
    @Test
    void findAll() throws Exception {
        // given
        Product product = productRepository.findById(1L).orElseThrow();

        // when
        ResultActions result = mockMvc.perform(get("/api/products"));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("성공"))
                .andExpect(jsonPath("$.data[0].name").value(product.getName()))
                .andExpect(jsonPath("$.data[0].price").value(product.getPrice()));
    }

    // GET /api/products/{id} - 단건 조회
    @Test
    void findById() throws Exception {
        // given
        Product product = productRepository.findById(1L).orElseThrow();

        // when
        ResultActions result = mockMvc.perform(get("/api/products/{id}", 1L));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("성공"))
                .andExpect(jsonPath("$.data.name").value(product.getName()))
                .andExpect(jsonPath("$.data.price").value(product.getPrice()));
    }

    // GET /api/products/{id} - 존재하지 않는 id 조회 시 404
    @Test
    void findById_notFound() throws Exception {
        // when
        ResultActions result = mockMvc.perform(get("/api/products/{id}", 9999L));

        // then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.result").value("실패"))
                .andExpect(jsonPath("$.message").value("상품을 찾을 수 없습니다."));
    }

    // POST /api/products - 생성
    @Test
    void create() throws Exception {
        // given
        Product newProduct = new Product("노트북", 1500000);

        // when
        ResultActions result = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProduct)));

        // then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.result").value("성공"))
                .andExpect(jsonPath("$.data.name").value("노트북"))
                .andExpect(jsonPath("$.data.price").value(1500000));
    }

    // POST /api/products - 이름 없이 생성 시 400
    @Test
    void create_fail_missingName() throws Exception {
        // given
        Product invalidProduct = new Product("", 1500000);

        // when
        ResultActions result = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidProduct)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value("실패"))
                .andExpect(jsonPath("$.message").value("상품명과 가격은 필수입니다."));
    }

    // PUT /api/products/{id} - 수정
    @Test
    void update() throws Exception {
        // given
        Product product = productRepository.findById(1L).orElseThrow();
        Product updateRequest = new Product("수정된상품", 2000000);

        // when
        ResultActions result = mockMvc.perform(put("/api/products/{id}", product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("성공"))
                .andExpect(jsonPath("$.data.name").value("수정된상품"))
                .andExpect(jsonPath("$.data.price").value(2000000));
    }

    // PUT /api/products/{id} - 존재하지 않는 id 수정 시 400
    @Test
    void update_notFound() throws Exception {
        // given
        Product updateRequest = new Product("수정된상품", 2000000);

        // when
        ResultActions result = mockMvc.perform(put("/api/products/{id}", 9999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value("실패"))
                .andExpect(jsonPath("$.message").value("상품 정보를 찾을 수 없거나 상품명 또는 가격이 입력되지 않았습니다."));
    }

    // DELETE /api/products/{id} - 삭제
    @Test
    void deleteById() throws Exception {
        // given
        Product saved = productRepository.save(new Product("삭제대상", 1000));

        // when
        ResultActions result = mockMvc.perform(delete("/api/products/{id}", saved.getId()));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("성공"));
    }

    // DELETE /api/products/{id} - 존재하지 않는 id 삭제 시 404
    @Test
    void deleteById_notFound() throws Exception {
        // when
        ResultActions result = mockMvc.perform(delete("/api/products/{id}", 9999L));

        // then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.result").value("실패"))
                .andExpect(jsonPath("$.message").value("상품을 찾을 수 없습니다."));
    }
}
