package site.moasis.monolithicbe.domain.article.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ArticleInfo {

    private UUID articleId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime createdBy;
    private String nickname;

}
