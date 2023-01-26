package site.moasis.monolithicbe.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.moasis.monolithicbe.common.response.CommonResponse;
import site.moasis.monolithicbe.domain.comment.service.CommentReadService;
import site.moasis.monolithicbe.domain.comment.service.CommentWriteService;
import java.util.UUID;
import static site.moasis.monolithicbe.domain.comment.vo.CommentVo.*;
import static site.moasis.monolithicbe.domain.comment.service.CommentCommand.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Tag(name = "CommentController", description = "댓글을 각 속성 값으로 조회 하고 생성, 삭제 가능하다")
public class CommentController {

    private final CommentReadService readService;
    private final CommentWriteService writeService;

    @GetMapping("/comments")
    @Operation(summary = "댓글 전체 조회")
    public ResponseEntity<?> findAll() {
        var commentInfo = readService.selectAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(commentInfo, "전체 댓글 조회 성공"));
    }

    @GetMapping("/comment/{commentId}")
    @Operation(summary = "댓글 단건 조회")
    public ResponseEntity<?> findOne(@PathVariable("commentId") UUID commentId) {
        var commentInfo = readService.selectOne(commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(commentInfo, "댓글 조회 성공"));
    }

    @GetMapping("/comments/users/{userId}")
    @Operation(summary = "user_id를 통한 댓글 조회")
    public ResponseEntity<?> findByUser(@PathVariable UUID userId) {
        var commentInfo = readService.selectByUser(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(commentInfo, "Id가 " + userId + "인 유저가 작성한 댓글 조회 성공"));
    }

    @GetMapping("/comments/articles/{articleId}")
    @Operation(summary = "article_id를 통한 댓글 조회")
    public ResponseEntity<?> findByArticle(@PathVariable UUID articleId) {
        var commentInfo = readService.selectByArticle(articleId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(commentInfo, articleId + "번 게시글 댓글 조회 성공"));
    }

    @PostMapping("/users/{userId}/articles/{articleId}/comments")
    @Operation(summary = "댓글 생성")
    public ResponseEntity<?> registerComment(
            @PathVariable UUID userId,
            @PathVariable UUID articleId,
            @RequestBody RegisterCommentRequest registerCommentRequest) {

        var command = RegisterCommentCommand
                .builder()
                .userId(userId)
                .articleId(articleId)
                .content(registerCommentRequest.getContent())
                .build();

        var commentInfo = writeService.registerComment(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(commentInfo, "댓글 생성 완료"));
    }

    @DeleteMapping("/comment/id/{commentId}")
    @Operation(summary = "id를 통한 댓글 삭제")
    public ResponseEntity<?> deleteOne(@PathVariable("commentId") UUID commentId) {
        var commentInfo = writeService.dropOne(commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(null, commentInfo + "번 댓글 삭제 완료"));
    }
}
