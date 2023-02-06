package site.moasis.monolithicbe.domain.comment;

import lombok.Getter;

import java.util.*;

public record CommentDto (){

    public record CommentOneDto(
            Comment comment) {
    }

    public record CommentResponseDto(
            List<Comment> comments) {
    }
}
