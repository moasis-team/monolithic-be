package site.moasis.monolithicbe.infrastructure.article;

import org.springframework.data.jpa.repository.JpaRepository;
import site.moasis.monolithicbe.domain.article.entity.Article;

import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {
}
