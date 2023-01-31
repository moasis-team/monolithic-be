package site.moasis.monolithicbe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.BusinessException;
import site.moasis.monolithicbe.common.exception.ErrorCode;
import site.moasis.monolithicbe.domain.comment.Comment;
import site.moasis.monolithicbe.domain.comment.CommentLikes;
import site.moasis.monolithicbe.domain.useraccount.repository.UserAccountRepository;
import site.moasis.monolithicbe.infrastructure.CommentLikesRepository;
import site.moasis.monolithicbe.infrastructure.CommentRepository;

import static site.moasis.monolithicbe.service.CommentCommand.*;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentWriteService{

    private final CommentRepository commentRepository;
    private final CommentLikesRepository commentLikesRepository;
    private final UserAccountRepository userAccountRepository;

    public CommentInfo registerComment(RegisterCommentCommand registerCommentCommand){

        UUID userId = userAccountRepository.selectIdByEmail(getUserEmail()).orElseThrow(()->
                new BusinessException(ErrorCode.UNAUTHORIZED, "user.byEmail", null));

        var comment = Comment.builder()
                .content(registerCommentCommand.getContent())
                .articleId(registerCommentCommand.getArticleId())
                .userId(userId)
                .build();

        return CommentInfoMapper.INSTANCE.toCommentInfo(commentRepository.save(comment));
    }

    public UUID deleteComment(UUID commentId){
        commentRepository.selectById(commentId).orElseThrow(() -> 
                new BusinessException(ErrorCode.NOT_FOUND, "comment.byId", List.of(commentId.toString())));
        commentRepository.deleteByCommentId(commentId);
        
        return commentId;
    }

    public Boolean likeComment(UUID articleId, UUID commentId) {
        String userEmail = getUserEmail();
        Comment comment = commentRepository.selectById(commentId).orElseThrow(()->
                new BusinessException(ErrorCode.NOT_FOUND, "comment.byId", List.of(commentId.toString())));
        Boolean exist = commentLikesRepository.existsByCommentIdAndCreateBy(commentId, userEmail);

        if (exist)
            commentLikesRepository.deleteLikes(commentId, userEmail);
        else
            commentLikesRepository.save(new CommentLikes(commentId, articleId, userEmail));

        comment.setIsLiked(!exist);
        return comment.getIsLiked();
    }

    private String getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getClass().equals(AnonymousAuthenticationToken.class))
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "user.byEmail", null);
        return authentication.getName();
    }
}
