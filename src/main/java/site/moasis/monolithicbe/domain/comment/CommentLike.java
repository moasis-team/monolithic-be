package site.moasis.monolithicbe.domain.comment;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CommentLike {

    @Id @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column
    private UUID commentId;

    @Column
    private UUID articleId;

    @Column
    private String createBy;

    @Builder
    public CommentLike(UUID commentId, UUID articleId, String createBy) {
        this.commentId = commentId;
        this.articleId = articleId;
        this.createBy = createBy;
    }
}
