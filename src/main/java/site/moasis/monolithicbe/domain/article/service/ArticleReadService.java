package site.moasis.monolithicbe.domain.article.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.BusinessException;
import site.moasis.monolithicbe.common.exception.ErrorCode;
import site.moasis.monolithicbe.domain.article.entity.Article;
import site.moasis.monolithicbe.domain.article.repository.ArticleRepository;

import java.util.List;
import java.util.UUID;

import static site.moasis.monolithicbe.domain.article.dto.ArticleDto.ArticleOneDto;
import static site.moasis.monolithicbe.domain.article.dto.ArticleDto.ArticleResponseDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleReadService {
    private final ArticleRepository articleRepository;

    public List<Article> searchArticles() {

        return articleRepository.findAll();
    }

    private ArticleResponseDto toDto(List<Article> articles) {
        return new ArticleResponseDto(articles);
    }

    public ArticleOneDto searchOne(UUID articleId) {
        Article article = articleRepository.findById(articleId).
                orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND,
                        "article.byId", List.of(articleId.toString())));
        articleRepository.updateView(articleId);
        return new ArticleOneDto(article);
    }

    public ArticleResponseDto searchByUserId(UUID userId) {
        return toDto(articleRepository.findByUserIdContaining(userId));
    }

    public ArticleResponseDto searchBytitle(String title) {
        return toDto(articleRepository.findByTitleContaining(title));
    }

    public ArticleResponseDto searchBycontent(String content) {
        return toDto(articleRepository.findByContentContaining(content));
    }
}



