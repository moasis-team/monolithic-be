package site.moasis.monolithicbe.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.EntityNotFoundException;
import site.moasis.monolithicbe.common.response.ErrorCode;
import site.moasis.monolithicbe.domain.comment.dto.CommentDto;
import site.moasis.monolithicbe.domain.comment.entity.Comment;
import site.moasis.monolithicbe.domain.comment.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentReadService{

    private final CommentRepository commentRepository;

    public CommentDto.commentOneDto retrieveCommentById(Long commentId){
        return toDto(Optional.ofNullable(commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new)));
    }
    public CommentDto.commentResponseDto retrieveCommentByArticleId(Long articleId){
        return toDto(commentRepository.findByArticleId(articleId));
    }
    public CommentDto.commentResponseDto retrieveCommentByUserId(Long userId){
        return toDto(commentRepository.findByUserId(userId));
    }
    public CommentDto.commentResponseDto toDto(List<Comment> commentList){
        return new CommentDto.commentResponseDto(commentList);
    }
    public CommentDto.commentOneDto toDto(Optional<Comment> comment){
        return new CommentDto.commentOneDto(comment);
    }
}
