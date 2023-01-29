package site.moasis.monolithicbe.domain.comment.service;

import javax.annotation.processing.Generated;
import site.moasis.monolithicbe.domain.comment.entity.Comment;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
<<<<<<< Updated upstream:src/main/generated/site/moasis/monolithicbe/domain/comment/service/CommentInfoMapperImpl.java
    date = "2023-01-24T18:03:11+0900",
=======
    date = "2023-01-29T19:28:04+0900",
>>>>>>> Stashed changes:src/main/generated/site/moasis/monolithicbe/service/CommentInfoMapperImpl.java
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
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
        commentInfo.setUserName( comment.getUserName() );
        commentInfo.setContent( comment.getContent() );
        commentInfo.setIsDeleted( comment.getIsDeleted() );

        return commentInfo;
    }
}
