package site.moasis.monolithicbe.domain.comment;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import site.moasis.monolithicbe.domain.common.DateTimeEntity;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@SQLDelete(sql = "UPDATE comment SET is_deleted=true WHERE comment_id = ?")
public class Comment extends DateTimeEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String content;

    @Column
    private UUID articleId;

    @Column
    private UUID userId;

    @Column
    private Boolean isDeleted = false;

    @Transient
    private Boolean isLiked;

    @Transient
    private Long likes;

    @Column
    private String userName;

    @Builder
    public Comment(String content, UUID articleId, String userName) {
        this.content = content;
        this.articleId = articleId;
        this.userName = userName;
    }

    public void setIsLiked(Boolean liked) {
        isLiked = liked;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }
}