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
import site.moasis.monolithicbe.infrastructure.CommentLikesRepository;
import site.moasis.monolithicbe.infrastructure.comment.CommentRepository;

import java.util.List;
import java.util.UUID;

import static site.moasis.monolithicbe.domain.comment.CommentDto.CommentOneDto;
import static site.moasis.monolithicbe.domain.comment.CommentDto.CommentResponseDto;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentReadService{

    private final CommentRepository commentRepository;
    private final CommentLikesRepository commentLikesRepository;

    public CommentOneDto selectOne(UUID commentId){
        Comment comment = commentRepository.selectById(commentId).orElseThrow(
                ()->new BusinessException(ErrorCode.NOT_FOUND, "comment.byId", List.of(commentId.toString())));
        comment.setLikes(getLikes(commentId));
        setUserLikes(commentId, comment);
        return new CommentOneDto(comment);
    }

    public CommentResponseDto selectByArticle(UUID articleId){
        List<Comment> comments = commentRepository.selectByArticleId(articleId);
        comments.forEach(c -> c.setLikes(getLikes(c.getId())));
        comments.forEach(c->setUserLikes(c.getId(), c));
        return new CommentResponseDto(comments);
    }

    public CommentResponseDto selectByUser(UUID userId){
        List<Comment> comments = commentRepository.selectByUserId(userId);
        comments.forEach(c -> c.setLikes(getLikes(c.getId())));
        comments.forEach(c->setUserLikes(c.getId(), c));
        return new CommentResponseDto(comments);
    }

    public Long getLikes(UUID commentId){
        return commentLikesRepository.countByCommentId(commentId);
    }

    private void setUserLikes(UUID commentId, Comment comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getClass().equals(AnonymousAuthenticationToken.class))
            comment.setIsLiked(null);
        else {
            Boolean exist = commentLikesRepository.existsByCommentIdAndCreateBy(commentId, authentication.getName());
            comment.setIsLiked(exist);
        }
    }
}
