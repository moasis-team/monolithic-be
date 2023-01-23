package site.moasis.monolithicbe.domain.comment.dto;

import site.moasis.monolithicbe.domain.comment.entity.Comment;

import java.util.*;

public class CommentDto {
    public record CommentCreateDto(String content, Long articleId, Long userId) {
    }

    public record CommentOneDto(Optional<Comment> comment) {
    }

    public record CommentResponseDto(List<Comment> comments) {
    }
}
