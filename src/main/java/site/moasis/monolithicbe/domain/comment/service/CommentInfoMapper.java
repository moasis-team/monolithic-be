package site.moasis.monolithicbe.domain.comment.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import site.moasis.monolithicbe.domain.comment.entity.Comment;
import static site.moasis.monolithicbe.domain.comment.dto.CommentDto.CommentInfo;

@Mapper
public interface CommentInfoMapper {

    CommentInfoMapper INSTANCE = Mappers.getMapper(CommentInfoMapper.class);

    @Mapping(target = "commentId", source = "comment.id")
    @Mapping(target = "createdAt", source = "comment.createdAt")
    CommentInfo toCommentInfo(Comment comment);
}
