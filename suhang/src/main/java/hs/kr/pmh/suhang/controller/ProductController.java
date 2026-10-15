package hs.kr.pmh.suhang.controller;

import hs.kr.pmh.suhang.domain.Product;
import hs.kr.pmh.suhang.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "상품 관리 API")
public class ProductController {

    private final ProductService productService;

    // POST /api/products
    // 요청 Body로 받은 상품 정보(이름, 가격)를 저장하고, 저장된 상품 데이터를 반환한다.
    @Operation(summary = "상품 등록", description = "상품명과 가격을 입력받아 새 상품을 등록합니다.")
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Product product) {
        Product created = productService.create(product);
        if (created == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("result", "실패", "message", "상품명과 가격은 필수입니다."));
        } else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("result", "성공", "data", created));
        }
    }

    // GET /api/products
    // 저장된 모든 상품 목록을 조회하여 반환한다.
    @Operation(summary = "전체 상품 조회", description = "저장된 모든 상품 목록을 반환합니다.")
    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(Map.of("result", "성공", "data", products));
    }

    // GET /api/products/{id}
    // 경로 변수로 받은 ID에 해당하는 상품 정보를 조회하여 반환한다.
    @Operation(summary = "상품 단건 조회", description = "ID로 특정 상품 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("result", "실패", "message", "상품을 찾을 수 없습니다."));
        } else {
            return ResponseEntity.ok(Map.of("result", "성공", "data", product));
        }
    }

    // PUT /api/products/{id}
    // 경로 변수로 받은 ID에 해당하는 상품의 이름과 가격을 수정하고, 수정된 상품 데이터를 반환한다.
    @Operation(summary = "상품 정보 수정", description = "ID로 특정 상품의 이름과 가격을 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody Product product) {
        Product updated = productService.update(id, product);
        if (updated == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("result", "실패", "message", "상품 정보를 찾을 수 없거나 상품명 또는 가격이 입력되지 않았습니다."));
        } else {
            return ResponseEntity.ok(Map.of("result", "성공", "data", updated));
        }
    }

    // DELETE /api/products/{id}
    // 경로 변수로 받은 ID에 해당하는 상품을 삭제하고, 성공 여부를 반환한다.
    @Operation(summary = "상품 삭제", description = "ID로 특정 상품을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        if (productService.delete(id)) {
            return ResponseEntity.ok(Map.of("result", "성공"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("result", "실패", "message", "상품을 찾을 수 없습니다."));
        }
    }
}
