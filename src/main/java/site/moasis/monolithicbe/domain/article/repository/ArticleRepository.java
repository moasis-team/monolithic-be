package site.moasis.monolithicbe.domain.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.moasis.monolithicbe.domain.article.entity.Article;
import site.moasis.monolithicbe.domain.comment.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

    List<Article> findByUserId(UUID userId);
    List<Article> findByTitle(String title);
    Optional<Article> findById(UUID articleId);
    List<Article> findByContent(String content);

    @Query(value = "select c from Article as c")
    List<Article> selectAll();
    void deleteById(UUID articleId);
}
