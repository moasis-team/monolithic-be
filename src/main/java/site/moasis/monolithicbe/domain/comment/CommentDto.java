package site.moasis.monolithicbe.domain.comment;

import java.util.*;

public record CommentDto (){

    public record CommentOneDto(
            Optional<Comment> comment) {
    }

    public record CommentResponseDto(
            List<Comment> comments) {
    }
}
