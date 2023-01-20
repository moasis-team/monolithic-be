package site.moasis.monolithicbe.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.EntityNotFoundException;
import site.moasis.monolithicbe.domain.comment.entity.Comment;
import site.moasis.monolithicbe.domain.comment.repository.CommentRepository;

import static site.moasis.monolithicbe.domain.comment.dto.CommentDto.CommentCreateDto;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentWriteService{

    private final CommentRepository commentRepository;

    public void createComment(CommentCreateDto commentCreateDto){
        Comment commentEntity = Comment.builder()
                .content(commentCreateDto.content())
                .articleId(commentCreateDto.articleId())
                .userId(commentCreateDto.userId())
                .build();
        commentRepository.save(commentEntity);
    }

    public void deleteComment(Long commentId){
        commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);
        commentRepository.deleteById(commentId);
    }
}
