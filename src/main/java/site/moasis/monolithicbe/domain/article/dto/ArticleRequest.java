package site.moasis.monolithicbe.domain.article.dto;

import java.util.Objects;

public record ArticleRequest(
        String title,
        String content

) {
    public static ArticleRequest of(String title, String content) {
        return new ArticleRequest(title, content);
    }
}
