package site.moasis.monolithicbe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.BusinessException;
import site.moasis.monolithicbe.common.exception.ErrorCode;
import site.moasis.monolithicbe.domain.comment.Comment;
import site.moasis.monolithicbe.infrastructure.CommentRepository;

import java.util.List;
import java.util.UUID;

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

    public UUID deleteComment(UUID commentId){
        commentRepository.findById(commentId).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_FOUND, "comment.byId", List.of(commentId.toString())));
        commentRepository.deleteById(commentId);
        return commentId;
    }
}
