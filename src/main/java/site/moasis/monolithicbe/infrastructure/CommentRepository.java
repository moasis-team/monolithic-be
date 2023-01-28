package site.moasis.monolithicbe.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.moasis.monolithicbe.domain.comment.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select c from Comment as c where c.articleId = :articleId")
    List<Comment> selectByArticleId(@Param("articleId")UUID articleId);

    @Query(value = "select c from Comment as c where c.userId = :userId")
    List<Comment> selectByUserId(@Param("userId") UUID userId);

    @Query(value = "select c from Comment as c where c.id = :id")
    Optional<Comment> selectById(@Param("id") UUID commentID);

    @Query(value = "select c from Comment as c")
    List<Comment> selectAll();

    @Modifying @Query(value = "delete from Comment as c where c.id = :id")
    void deleteByCommentId(@Param("id") UUID commentID);
}
