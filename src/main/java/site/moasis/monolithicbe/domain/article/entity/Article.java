package site.moasis.monolithicbe.domain.article.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@SQLDelete(sql = "UPDATE article SET is_deleted=true WHERE article_id = ?")
public class Article {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String content;

    @Builder
    public Article(String content) {
        this.content = content;
    }
}
