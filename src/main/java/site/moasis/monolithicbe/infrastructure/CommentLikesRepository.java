package site.moasis.monolithicbe.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.moasis.monolithicbe.domain.comment.CommentLikes;

import java.util.UUID;

public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long> {

//    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "select count(c) from CommentLikes as c where c.commentId = :commentId")
    Long countByCommentId(@Param("commentId") UUID commentId);

    @Modifying @Query(value = "delete from CommentLikes as c where c.commentId = :commentId and c.createBy = :userEmail")
    void deleteLikes(@Param("commentId") UUID commentId, @Param("userEmail") String userEmail);

    Boolean existsByCommentIdAndCreateBy(UUID commentId, String email);
}
