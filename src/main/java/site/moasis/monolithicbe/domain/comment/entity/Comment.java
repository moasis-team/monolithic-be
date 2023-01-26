package site.moasis.monolithicbe.domain.comment.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import site.moasis.monolithicbe.domain.common.DateTimeEntity;

import java.util.UUID;

@Getter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@SQLDelete(sql = "UPDATE comment SET is_deleted=true WHERE comment_id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends DateTimeEntity {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(generator = "UUID")
    @Column(name = "comment_id", columnDefinition = "BINARY(16)")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column
    private String content;

    @Column
    @NonNull
    private UUID articleId;

    @Column
    @NonNull
    private UUID userId;
    @Column
    private Boolean isDeleted = false;

    @Builder
    public Comment(String content, UUID articleId, UUID userId) {
        this.content = content;
        this.articleId = articleId;
        this.userId = userId;
    }
}