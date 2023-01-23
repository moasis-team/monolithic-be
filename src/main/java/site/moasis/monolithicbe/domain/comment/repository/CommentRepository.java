package site.moasis.monolithicbe.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.moasis.monolithicbe.domain.comment.entity.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(Long articleId);
    List<Comment> findByUserId(Long userId);

    Optional<Comment> findById(UUID commentID);

    Optional<Comment> deleteById(UUID commentID);
    void deleteByUserId(Long userId);
    void deleteByArticleId(Long articleId);
}
