package site.moasis.monolithicbe.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.moasis.monolithicbe.domain.comment.CommentLike;

import java.util.UUID;

public interface CommentLikesRepository extends JpaRepository<CommentLike, Long> {

//    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "select count(c) from CommentLike as c where c.commentId = :commentId")
    Long countByCommentId(@Param("commentId") UUID commentId);

    @Modifying @Query(value = "delete from CommentLike as c where c.commentId = :commentId and c.createBy = :userEmail")
    void deleteLikes(@Param("commentId") UUID commentId, @Param("userEmail") String userEmail);

    Boolean existsByCommentIdAndCreateBy(UUID commentId, String email);
}
