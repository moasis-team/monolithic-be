package site.moasis.monolithicbe.domain.comment;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
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
    @GeneratedValue(generator = "UUID")
    @Column(name = "comment_id", columnDefinition = "BINARY(16)")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column
    private String content;

    @Column
    private UUID articleId;

    @Column
    private UUID userId;

    @Column
    private Boolean isDeleted = false;

    @Column
    private String userName;

    @Builder
    public Comment(String content, UUID articleId, UUID userId, String userName) {
        this.content = content;
        this.articleId = articleId;
        this.userId = userId;
        this.userName = userName;
    }
}