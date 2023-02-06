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
import site.moasis.monolithicbe.domain.article.entity.Article;
import site.moasis.monolithicbe.domain.comment.Comment;
import site.moasis.monolithicbe.domain.comment.CommentLike;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.repository.UserAccountRepository;
import site.moasis.monolithicbe.infrastructure.CommentLikesRepository;
import site.moasis.monolithicbe.infrastructure.article.ArticleRepository;
import site.moasis.monolithicbe.infrastructure.comment.CommentRepository;

import java.util.List;
import java.util.UUID;

import static site.moasis.monolithicbe.service.CommentCommand.RegisterCommentCommand;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentWriteService{

    private final CommentRepository commentRepository;

    private final CommentLikesRepository commentLikesRepository;
    private final UserAccountRepository userAccountRepository;
    private final ArticleRepository articleRepository;

    public CommentInfo registerComment(RegisterCommentCommand registerCommentCommand){

        UUID userId = userAccountRepository.selectIdByEmail(getUserEmail()).orElseThrow(()->
                new BusinessException(ErrorCode.UNAUTHORIZED, "user.byEmail", null));

        var comment = Comment.builder()
                .content(registerCommentCommand.getContent())
                .articleId(registerCommentCommand.getArticleId())
                .userName(getUserEmail())
                .build();

        return CommentInfoMapper.INSTANCE.toCommentInfo(commentRepository.save(comment));
    }

    public void deleteComment(CommentCommand.DeleteCommentCommand deleteCommentCommand){
        UserAccount loginUserAccount = getUserById(deleteCommentCommand.getUserId());
        getArticleById(deleteCommentCommand.getArticleId());

        Comment comment = getCommentById(deleteCommentCommand.getCommentId());

        if (!comment.getUserId().equals(loginUserAccount.getId())) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "comment", null);
        }
        commentRepository.deleteById(deleteCommentCommand.getCommentId());
    }

    private UserAccount getUserById(UUID userId) {
        return userAccountRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "user.byId", List.of(userId.toString())));
    }

    private Article getArticleById(UUID articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "article.byId", List.of(articleId.toString())));
    }

    private Comment getCommentById(UUID commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(
                        () -> new BusinessException(ErrorCode.NOT_FOUND, "comment.byId", List.of(commentId.toString())));
    }

    public Boolean likeComment(UUID articleId, UUID commentId) {
        String userEmail = getUserEmail();
        Comment comment = commentRepository.selectById(commentId).orElseThrow(()->
                new BusinessException(ErrorCode.NOT_FOUND, "comment.byId", List.of(commentId.toString())));
        Boolean exist = commentLikesRepository.existsByCommentIdAndCreateBy(commentId, userEmail);

        if (exist)
            commentLikesRepository.deleteLikes(commentId, userEmail);
        else
            commentLikesRepository.save(new CommentLike(commentId, articleId, userEmail));

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
