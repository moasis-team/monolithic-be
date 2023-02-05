package site.moasis.monolithicbe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.BusinessException;
import site.moasis.monolithicbe.common.exception.ErrorCode;
import site.moasis.monolithicbe.domain.comment.Comment;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.repository.UserAccountRepository;
import site.moasis.monolithicbe.infrastructure.CommentRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentWriteService{

    private final CommentRepository commentRepository;
    private final UserAccountRepository userAccountRepository;

    public CommentInfo registerComment(CommentCommand.RegisterCommentCommand registerCommentCommand){
        UserAccount userAccount = getUserById(registerCommentCommand.getUserId());

        var comment = Comment.builder()
                .content(registerCommentCommand.getContent())
                .articleId(registerCommentCommand.getArticleId())
                .userId(registerCommentCommand.getUserId())
                .userName(userAccount.getName())
                .build();

        return CommentInfoMapper.INSTANCE.toCommentInfo(commentRepository.save(comment));
    }

    public UUID deleteComment(UUID commentId){
        commentRepository.selectById(commentId).orElseThrow(() -> 
                new BusinessException(ErrorCode.NOT_FOUND, "comment.byId", List.of(commentId.toString())));
        commentRepository.deleteByCommentId(commentId);
        
        return commentId;
    }

    private UserAccount getUserById(UUID userId) {
        return userAccountRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "user.byId", List.of(userId.toString())));
    }
}
