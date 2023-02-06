package site.moasis.monolithicbe.controller.comment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.moasis.monolithicbe.controller.common.CommonResponse;
import site.moasis.monolithicbe.service.CommentCommand;
import site.moasis.monolithicbe.service.CommentWriteService;

import java.util.UUID;

import static site.moasis.monolithicbe.service.CommentCommand.RegisterCommentCommand;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles/{articleId}/comments")
@Tag(name = "CommentController", description = "댓글 생성, 삭제 기능")
public class CommentWriteController {
    private final CommentWriteService writeService;

    @PostMapping
    @Operation(summary = "댓글 생성")
    public ResponseEntity<CommonResponse<?>> registerComment(
            @PathVariable UUID articleId,
            @RequestBody RegisterCommentRequest registerCommentRequest) {

        var command = RegisterCommentCommand
                .builder()
                .articleId(articleId)
                .content(registerCommentRequest.getContent())
                .build();

        var commentInfo = writeService.registerComment(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(commentInfo, "댓글 생성 완료"));
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<CommonResponse<?>> deleteComment(
            @PathVariable("articleId") UUID articleId,
            @PathVariable("commentId") UUID commentId) {

        CommentCommand.DeleteCommentCommand command = CommentCommand.DeleteCommentCommand.builder()
                .articleId(articleId)
                .commentId(commentId)
                .build();

        writeService.deleteComment(command);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(command, command.getCommentId() + "댓글 삭제 성공"));
    }

    @PostMapping("/{commentId}")
    @Operation(summary = "댓글 좋아요 또는 취소")
    public ResponseEntity<CommonResponse<?>> likeComment(
            @PathVariable("articleId") UUID articleId,
            @PathVariable("commentId") UUID commentId
    ) {
        Boolean commentInfo = writeService.likeComment(articleId, commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(commentInfo, "현재 상태 : " + commentInfo));
    }

    @Getter
    @EqualsAndHashCode
    @NoArgsConstructor
    public static class RegisterCommentRequest {
        private String content;
    }
}
