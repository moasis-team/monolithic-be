package site.moasis.monolithicbe.domain.comment.dto;

import site.moasis.monolithicbe.domain.comment.entity.Comment;

import java.util.*;

public class CommentDto {
    public record commentCreateDto(String content, Long articleId, Long userId) {
    }

    public record commentOneDto(Optional<Comment> comment) {
    }

    public record commentResponseDto(List<Comment> commentList) {
    }
}
