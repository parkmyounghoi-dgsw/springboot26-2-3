package hs.kr.pmh.suhang.service;

import hs.kr.pmh.suhang.domain.Article;
import hs.kr.pmh.suhang.domain.Member;
import hs.kr.pmh.suhang.repository.ArticleRepository;
import hs.kr.pmh.suhang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Override
    public Article create(Long memberId, Article article) {
        if (!StringUtils.hasText(article.getTitle()) || !StringUtils.hasText(article.getContent())) {
            return null;
        }
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) {
            return null;
        }
        Article newArticle = new Article(article.getTitle(), article.getContent(), member);
        return articleRepository.save(newArticle);
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> findByMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) {
            return null;
        }
        return articleRepository.findByMember(member);
    }

    @Override
    public Article findById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Article update(Long id, Article article) {
        Article existing = articleRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        if (!StringUtils.hasText(article.getTitle()) || !StringUtils.hasText(article.getContent())) {
            return null;
        }
        existing.update(article.getTitle(), article.getContent());
        return articleRepository.save(existing);
    }

    @Override
    public boolean delete(Long id) {
        if (articleRepository.existsById(id)) {
            articleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
