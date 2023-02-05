package site.moasis.monolithicbe.service;

import javax.annotation.processing.Generated;
import site.moasis.monolithicbe.domain.comment.Comment;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-27T23:07:40+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
public class CommentInfoMapperImpl implements CommentInfoMapper {

    @Override
    public CommentInfo toCommentInfo(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentInfo commentInfo = new CommentInfo();

        commentInfo.setCommentId( comment.getId() );
        commentInfo.setCreatedAt( comment.getCreatedAt() );
        commentInfo.setContent( comment.getContent() );
        commentInfo.setIsDeleted( comment.getIsDeleted() );

        return commentInfo;
    }
}
