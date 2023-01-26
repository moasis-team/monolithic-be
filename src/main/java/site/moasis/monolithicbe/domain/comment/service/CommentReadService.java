package site.moasis.monolithicbe.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.BusinessException;
import site.moasis.monolithicbe.common.response.ErrorCode;
import site.moasis.monolithicbe.domain.comment.repository.CommentRepository;
import java.util.Optional;
import java.util.UUID;
import static site.moasis.monolithicbe.domain.comment.dto.CommentDto.CommentResponseDto;
import static site.moasis.monolithicbe.domain.comment.dto.CommentDto.CommentOneDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentReadService{

    private final CommentRepository commentRepository;

    public CommentResponseDto selectAll(){
        return new CommentResponseDto(commentRepository.findAll());
    }

    public CommentOneDto selectOne(UUID commentId){
        return new CommentOneDto(
                Optional.ofNullable(commentRepository.findById(commentId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND))));
    }

    public CommentResponseDto selectByArticle(UUID articleId){
        return new CommentResponseDto(
                commentRepository.findByArticleId(articleId));
    }

    public CommentResponseDto selectByUser(UUID userId){
        return new CommentResponseDto(
                commentRepository.findByUserId(userId));
    }
}
