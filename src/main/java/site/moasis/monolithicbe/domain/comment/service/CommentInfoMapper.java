package site.moasis.monolithicbe.domain.comment.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import site.moasis.monolithicbe.domain.comment.entity.Comment;

@Mapper
public interface CommentInfoMapper {

    CommentInfoMapper INSTANCE = Mappers.getMapper(CommentInfoMapper.class);

    @Mapping(target = "commentId", source = "comment.id")
    CommentInfo toCommentInfo(Comment comment);
}
