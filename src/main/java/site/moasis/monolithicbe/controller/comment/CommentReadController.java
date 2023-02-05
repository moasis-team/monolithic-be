package site.moasis.monolithicbe.controller.comment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.moasis.monolithicbe.controller.common.CommonResponse;
import site.moasis.monolithicbe.service.CommentReadService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles/{articleId}/comments")
@Tag(name = "CommentController", description = "댓글 조회 기능")
public class CommentReadController {

    private final CommentReadService readService;

    @GetMapping("/users/{userId}")
    @Operation(summary = "user_id를 통한 댓글 조회")
    public ResponseEntity<CommonResponse<?>> findByUser(@PathVariable UUID userId) {
        var commentInfo = readService.selectByUser(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(commentInfo, "Id가 " + userId + "인 유저가 작성한 댓글 조회 성공"));
    }

    @GetMapping
    @Operation(summary = "article_id를 통한 댓글 조회")
    public ResponseEntity<CommonResponse<?>> findByArticle(@PathVariable UUID articleId) {
        var commentInfo = readService.selectByArticle(articleId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(commentInfo, articleId + "번 게시글 댓글 조회 성공"));
    }
}
