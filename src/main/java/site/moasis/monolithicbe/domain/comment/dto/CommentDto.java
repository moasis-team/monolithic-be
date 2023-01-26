package site.moasis.monolithicbe.domain.comment.dto;

import site.moasis.monolithicbe.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import java.util.*;

public record CommentDto (){

    public record CommentInfo (
            UUID commentId,
            String content,
            LocalDateTime createdAt,
            Boolean isDeleted,
            String nickname) {
    }

    public record CommentOneDto(
            Optional<Comment> comment) {
    }

    public record CommentResponseDto(
            List<Comment> comments) {
    }
}
