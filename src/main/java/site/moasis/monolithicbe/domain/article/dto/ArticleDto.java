package site.moasis.monolithicbe.domain.article.dto;

import site.moasis.monolithicbe.domain.article.entity.Article;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ArticleDto(
        UUID id,
        UUID userId,
        String title,
        String content,
        Long view,
        LocalDateTime createdAt,
        LocalDateTime updateAt
) {
    public record ArticleOneDto(Article article) {
    }

    public record ArticleResponseDto(List<Article> articles) {
    }


}
