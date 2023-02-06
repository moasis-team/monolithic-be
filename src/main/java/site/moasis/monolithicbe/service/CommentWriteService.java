package site.moasis.monolithicbe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.BusinessException;
import site.moasis.monolithicbe.common.exception.ErrorCode;
import site.moasis.monolithicbe.domain.article.entity.Article;
import site.moasis.monolithicbe.domain.article.repository.ArticleRepository;
import site.moasis.monolithicbe.domain.comment.Comment;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.repository.UserAccountRepository;
import site.moasis.monolithicbe.infrastructure.comment.CommentRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentWriteService{

    private final CommentRepository commentRepository;
    private final UserAccountRepository userAccountRepository;
    private final ArticleRepository articleRepository;

    public CommentInfo registerComment(CommentCommand.RegisterCommentCommand registerCommentCommand){
        UserAccount userAccount = getUserById(registerCommentCommand.getUserId());
        Article article = getArticleById(registerCommentCommand.getArticleId());

        Comment comment = Comment.builder()
                .content(registerCommentCommand.getContent())
                .articleId(article.getId())
                .userId(userAccount.getId())
                .userName(userAccount.getName())
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
}
