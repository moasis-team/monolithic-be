package site.moasis.monolithicbe.domain.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.BusinessException;
import site.moasis.monolithicbe.common.response.ErrorCode;
import site.moasis.monolithicbe.domain.comment.entity.Comment;
import site.moasis.monolithicbe.domain.comment.repository.CommentRepository;
import java.util.UUID;
import static site.moasis.monolithicbe.domain.comment.dto.CommentDto.*;

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
        commentRepository.findById(commentId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
        commentRepository.deleteById(commentId);
        return commentId;
    }
}
