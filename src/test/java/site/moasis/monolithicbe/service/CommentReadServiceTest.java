package site.moasis.monolithicbe.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.domain.comment.Comment;
import site.moasis.monolithicbe.domain.useraccount.repository.UserAccountRepository;
import site.moasis.monolithicbe.infrastructure.comment.CommentRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CommentReadServiceTest {

    @Autowired
    CommentReadService commentReadService;
    @Autowired
    CommentWriteService commentWriteService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserAccountRepository userAccountRepository;

    UUID randomUUID;
    UUID commentId;

    @BeforeEach
    void beforeEach() {
        randomUUID = UUID.randomUUID();
        Comment newComment = Comment.builder()
                .articleId(randomUUID)
                .content(randomUUID.toString())
                .build();
        Comment storedComment = commentRepository.save(newComment);
        commentId = storedComment.getId();
    }

    @Test
    void 좋아요_기능이_정상적으로_작동하는지_확인한다() {

        // given
        authentication();

        // when
        for (int i = 0; i < 101; i++)
            commentWriteService.likeComment(randomUUID, commentId);
        // then
        assertThat(commentReadService.selectOne(commentId).comment().getLikes()).isEqualTo(1L);
    }

    @Test
    void 취소_기능이_정상적으로_작동하는지_확인한다() {

        // given
        authentication();
        // when
        for (int i = 0; i < 100; i++)
            commentWriteService.likeComment(randomUUID, commentId);
        // then
        assertThat(commentReadService.selectOne(commentId).comment().getLikes()).isEqualTo(0L);
    }

    private void authentication() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(null, null));
    }
}