package site.moasis.monolithicbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.BusinessException;
import site.moasis.monolithicbe.common.exception.ErrorCode;
import site.moasis.monolithicbe.infrastructure.comment.CommentRepository;
import java.util.Optional;
import java.util.UUID;
import static site.moasis.monolithicbe.domain.comment.CommentDto.CommentResponseDto;
import static site.moasis.monolithicbe.domain.comment.CommentDto.CommentOneDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentReadService{

    private final CommentRepository commentRepository;

    public CommentResponseDto selectAll(){
        return new CommentResponseDto(commentRepository.selectAll());
    }

    public CommentOneDto selectOne(UUID commentId){
        return new CommentOneDto(
                Optional.ofNullable(commentRepository.selectById(commentId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND))));
    }

    public CommentResponseDto selectByArticle(UUID articleId){
        return new CommentResponseDto(
                commentRepository.selectByArticleId(articleId));
    }

    public CommentResponseDto selectByUser(UUID userId){
        return new CommentResponseDto(
                commentRepository.selectByUserId(userId));
    }
}
