package site.moasis.monolithicbe.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.moasis.monolithicbe.domain.comment.entity.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(UUID articleId);
    List<Comment> findByUserId(UUID userId);

    Optional<Comment> findById(UUID commentID);

    void deleteById(UUID commentID);
}
