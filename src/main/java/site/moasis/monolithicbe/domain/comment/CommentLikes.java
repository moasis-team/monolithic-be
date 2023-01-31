package site.moasis.monolithicbe.domain.comment;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class CommentLikes {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private UUID commentId;

    @Column
    private UUID articleId;

    @Column
    private String createBy;

    @Builder
    public CommentLikes(UUID commentId, UUID articleId, String createBy) {
        this.commentId = commentId;
        this.articleId = articleId;
        this.createBy = createBy;
    }
}
