package site.moasis.monolithicbe.domain.comment.dto;

import site.moasis.monolithicbe.domain.comment.entity.Comment;

import java.util.*;

public class CommentDto {
    public static record CommentCreateDto(String content, Long articleId, Long userId) {
    }

    public static record CommentOneDto(Optional<Comment> comment) {
    }

    public static record CommentResponseDto(List<Comment> commentList) {
    }
}
