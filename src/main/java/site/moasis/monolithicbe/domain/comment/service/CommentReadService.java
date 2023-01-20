package site.moasis.monolithicbe.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.EntityNotFoundException;
import site.moasis.monolithicbe.domain.comment.repository.CommentRepository;
import java.util.Optional;

import static site.moasis.monolithicbe.domain.comment.dto.CommentDto.CommentResponseDto;
import static site.moasis.monolithicbe.domain.comment.dto.CommentDto.CommentOneDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentReadService{

    private final CommentRepository commentRepository;

    public CommentOneDto retrieveCommentById(Long commentId){
        return new CommentOneDto(
                Optional.ofNullable(commentRepository.findById(commentId)
                        .orElseThrow(EntityNotFoundException::new)));
    }
    public CommentResponseDto retrieveCommentByArticleId(Long articleId){
        return new CommentResponseDto(
                commentRepository.findByArticleId(articleId));
    }
    public CommentResponseDto retrieveCommentByUserId(Long userId){
        return new CommentResponseDto(
                commentRepository.findByUserId(userId));
    }
}
