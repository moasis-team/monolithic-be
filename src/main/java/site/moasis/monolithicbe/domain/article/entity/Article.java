package site.moasis.monolithicbe.domain.article.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import site.moasis.monolithicbe.domain.common.DateTimeEntity;

import java.util.UUID;


@Getter
@ToString(callSuper = true)

@Entity
public class Article extends DateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    @Setter
    private UUID userId;
    @Column(nullable = false)
    @Setter
    private String title;   // 제목

    @Column(nullable = false, length = 10000)
    @Setter
    private String content; // 본문
    @Column(nullable = false)
    private Long view;     // 조회수


    protected Article() {
    }

    @Builder
    public Article(UUID userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.view = 0L;

    }

    public static Article of(UUID userId, String title, String content) {
        return new Article(userId, title, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

}
