package hs.kr.pmh.suhang.service;

import hs.kr.pmh.suhang.domain.Article;

import java.util.List;

public interface ArticleService {

    // 글 작성 (memberId, article)
    Article create(Long memberId, Article article);

    // 전체 글 조회
    List<Article> findAll();

    // 특정 회원의 글 목록 조회
    List<Article> findByMember(Long memberId);

    // 글 단건 조회
    Article findById(Long id);

    // 글 수정
    Article update(Long id, Article article);

    // 글 삭제
    boolean delete(Long id);
}
