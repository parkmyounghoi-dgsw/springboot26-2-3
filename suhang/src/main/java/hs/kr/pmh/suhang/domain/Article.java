package hs.kr.pmh.suhang.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Schema(description = "블로그 글 정보")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "글 ID", accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long id;

    @Column(nullable = false, length = 200)
    @Schema(description = "글 제목", example = "Spring Boot 시작하기")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Schema(description = "글 내용", example = "오늘은 Spring Boot를 배워봅시다.")
    private String content;

    @Column(nullable = false, updatable = false)
    @Schema(description = "작성 일시", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Schema(description = "수정 일시", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @Schema(description = "작성자")
    private Member member;

    public Article(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }
}
