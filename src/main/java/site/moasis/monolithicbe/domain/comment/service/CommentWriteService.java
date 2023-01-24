package site.moasis.monolithicbe.domain.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.EntityNotFoundException;
import site.moasis.monolithicbe.domain.comment.entity.Comment;
import site.moasis.monolithicbe.domain.comment.repository.CommentRepository;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;

import java.util.UUID;

import static site.moasis.monolithicbe.domain.comment.dto.CommentDto.CommentCreateDto;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentWriteService{

    private final CommentRepository commentRepository;

    public CommentInfo registerComment(CommentCommand.RegisterCommentCommand registerCommentCommand){
        var comment = Comment.builder()
                .content(registerCommentCommand.getContent())
                .articleId(registerCommentCommand.getArticleId())
                .userId(registerCommentCommand.getUserId())
                .build();

        return CommentInfoMapper.INSTANCE.toCommentInfo(commentRepository.save(comment));
    }

    public UUID dropOne(UUID commentId){
        commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);
        commentRepository.deleteById(commentId);
        return commentId;
    }

    public Long dropByUser(Long userId) {
        commentRepository.deleteByUserId(userId);
        return userId;
    }

    public Long dropByArticle(Long articleId) {
        commentRepository.deleteByArticleId(articleId);
        return articleId;
    }
}
