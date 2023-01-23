package site.moasis.monolithicbe.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.moasis.monolithicbe.domain.comment.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(Long articleId);
    List<Comment> findByUserId(Long userId);
    void deleteByUserId(Long userId);
    void deleteByArticleId(Long articleId);
}
