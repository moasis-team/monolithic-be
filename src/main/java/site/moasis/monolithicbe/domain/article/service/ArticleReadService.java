package site.moasis.monolithicbe.domain.article.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.BusinessException;
import site.moasis.monolithicbe.common.exception.ErrorCode;
import site.moasis.monolithicbe.domain.article.entity.Article;
import site.moasis.monolithicbe.domain.article.repository.ArticleRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static site.moasis.monolithicbe.domain.article.dto.ArticleDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleReadService {
    private static ArticleRepository articleRepository;

    public ArticleReadService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public static articleResponseDto toDto(List<Article> article){
        return new articleResponseDto(article);
    }

    public articleResponseDto searchAll() {
        return new articleResponseDto(articleRepository.selectAll());
    }

    public articleOneDto searchOne(UUID articleId) {
        return new articleOneDto(Optional.ofNullable(articleRepository.findById(articleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND))));
    }

    public articleResponseDto searchByUserId(UUID userId) {
        return toDto(articleRepository.findByUserId(userId));
    }

    public articleResponseDto searchBytitle(String title) {
        return toDto(articleRepository.findByTitle(title));
    }

    public articleResponseDto searchBycontent(String content) {
        return toDto(articleRepository.findByContent(content));
    }
}

//    public long getArticleCount() {
//        return articleRepository.count();
//    }
//}



