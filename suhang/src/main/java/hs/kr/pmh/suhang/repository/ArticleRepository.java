package hs.kr.pmh.suhang.repository;

import hs.kr.pmh.suhang.domain.Article;
import hs.kr.pmh.suhang.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    // 특정 회원의 글 목록 조회
    List<Article> findByMember(Member member);
}
