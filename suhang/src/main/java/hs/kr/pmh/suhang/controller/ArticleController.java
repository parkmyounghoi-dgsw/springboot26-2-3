package hs.kr.pmh.suhang.controller;

import hs.kr.pmh.suhang.domain.Article;
import hs.kr.pmh.suhang.service.ArticleService;
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
@RequestMapping("/api/articles")
@RequiredArgsConstructor
@Tag(name = "Article", description = "블로그 글 관리 API")
public class ArticleController {

    private final ArticleService articleService;

    // POST /api/articles/member/{memberId}
    // 글 작성
    @Operation(summary = "글 작성", description = "특정 회원이 글을 작성합니다.")
    @PostMapping("/member/{memberId}")
    public ResponseEntity<Map<String, Object>> create(
            @PathVariable Long memberId,
            @RequestBody Article article) {
        Article created = articleService.create(memberId, article);
        if (created == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("result", "실패", "message", "회원 정보를 찾을 수 없거나 제목/내용은 필수입니다."));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("result", "성공", "data", created));
    }

    // GET /api/articles
    // 전체 글 조회
    @Operation(summary = "전체 글 조회", description = "등록된 모든 글 목록을 반환합니다.")
    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll() {
        List<Article> articles = articleService.findAll();
        return ResponseEntity.ok(Map.of("result", "성공", "data", articles));
    }

    // GET /api/articles/member/{memberId}
    // 특정 회원의 글 목록 조회
    @Operation(summary = "회원별 글 목록 조회", description = "특정 회원이 작성한 모든 글 목록을 반환합니다.")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<Map<String, Object>> findByMember(@PathVariable Long memberId) {
        List<Article> articles = articleService.findByMember(memberId);
        if (articles == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("result", "실패", "message", "회원을 찾을 수 없습니다."));
        }
        return ResponseEntity.ok(Map.of("result", "성공", "data", articles));
    }

    // GET /api/articles/{id}
    // 글 단건 조회
    @Operation(summary = "글 단건 조회", description = "ID로 특정 글을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        Article article = articleService.findById(id);
        if (article == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("result", "실패", "message", "글을 찾을 수 없습니다."));
        }
        return ResponseEntity.ok(Map.of("result", "성공", "data", article));
    }

    // PUT /api/articles/{id}
    // 글 수정
    @Operation(summary = "글 수정", description = "ID로 특정 글의 제목과 내용을 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Long id,
            @RequestBody Article article) {
        Article updated = articleService.update(id, article);
        if (updated == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("result", "실패", "message", "글을 찾을 수 없거나 제목/내용은 필수입니다."));
        }
        return ResponseEntity.ok(Map.of("result", "성공", "data", updated));
    }

    // DELETE /api/articles/{id}
    // 글 삭제
    @Operation(summary = "글 삭제", description = "ID로 특정 글을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        if (articleService.delete(id)) {
            return ResponseEntity.ok(Map.of("result", "성공"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("result", "실패", "message", "글을 찾을 수 없습니다."));
    }
}
