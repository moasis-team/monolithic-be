package site.moasis.monolithicbe.domain.article.dto;

import site.moasis.monolithicbe.domain.article.entity.Article;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record ArticleDto(
        UUID id,
        String articleId,
        String userId,
        String title,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public record articleCreateDto(String content, String articleId, String userId) {

    }

    public record articleUpdateDto(String title, String content, String articleId, UUID userId){

    }

    public record articleOneDto(Optional<Article> article) {

    }

    public record articleResponseDto(List<Article> article) {
    }
}
