package site.moasis.monolithicbe.domain.article.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;


@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})

@Entity
public class Article extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String userId;

    @Setter
    @Column(nullable = false)
    private String articleId;

    @Setter
    @Column(nullable = false)
    private String title;   // 제목

    @Setter
    @Column(nullable = false, length = 10000)
    private String content; // 본문

//    @Setter
//    @Column(nullable = false)
//    private UUID count;     // 조회수


    protected Article() {}
    @Builder
    private Article(String userId, String articleId, String title, String content){
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.articleId = articleId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
