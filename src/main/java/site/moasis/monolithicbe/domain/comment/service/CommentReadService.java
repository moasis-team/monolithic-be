package site.moasis.monolithicbe.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.EntityNotFoundException;
import site.moasis.monolithicbe.domain.comment.dto.CommentDto;
import site.moasis.monolithicbe.domain.comment.repository.CommentRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentReadService{

    private final CommentRepository commentRepository;

    public CommentDto.commentOneDto retrieveCommentById(Long commentId){
        return new CommentDto.commentOneDto(
                Optional.ofNullable(commentRepository.findById(commentId)
                        .orElseThrow(EntityNotFoundException::new)));
    }
    public CommentDto.commentResponseDto retrieveCommentByArticleId(Long articleId){
        return new CommentDto.commentResponseDto(
                commentRepository.findByArticleId(articleId));
    }
    public CommentDto.commentResponseDto retrieveCommentByUserId(Long userId){
        return new CommentDto.commentResponseDto(
                commentRepository.findByUserId(userId));
    }
}
