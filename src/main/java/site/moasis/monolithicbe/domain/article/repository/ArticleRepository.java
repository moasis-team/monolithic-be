package site.moasis.monolithicbe.domain.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.moasis.monolithicbe.domain.article.entity.Article;

import java.util.List;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

    List<Article> findByUserIdContaining(UUID userId);

    List<Article> findByTitleContaining(String title);

    List<Article> findByContentContaining(String content);

    @Query(value = "select c from Article as c ORDER BY c.createdAt DESC,c.updatedAt DESC")
    List<Article> findAll();

    @Modifying
    @Query("update Article q set q.view = q.view + 1 where q.id = :id")
    void updateView(@Param("id") UUID id);

}
