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
import site.moasis.monolithicbe.service.CommentReadService;
import site.moasis.monolithicbe.service.CommentWriteService;
import java.util.UUID;
import static site.moasis.monolithicbe.service.CommentCommand.RegisterCommentCommand;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/{userId}/articles/{articleId}") // todo: 스프링 시큐리티 기능 완성 후 유저 정보 가져올 수 있을 때 "/users/{userId}" 삭제
@Tag(name = "CommentController", description = "댓글 생성, 삭제 기능")
public class CommentWriteController {
    private final CommentReadService readService;
    private final CommentWriteService writeService;

    @PostMapping("/comments")
    @Operation(summary = "댓글 생성")
    public ResponseEntity<CommonResponse<?>> registerComment(
            @PathVariable UUID userId,
            @PathVariable UUID articleId,
            @RequestBody RegisterCommentRequest registerCommentRequest) {

        UUID loginUserId = userId;

        var command = RegisterCommentCommand
                .builder()
                .userId(loginUserId)
                .articleId(articleId)
                .content(registerCommentRequest.getContent())
                .build();

        var commentInfo = writeService.registerComment(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(commentInfo, "댓글 생성 완료"));
    }

    @DeleteMapping("/comments/{commentId}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<CommonResponse<?>> deleteComment(
            @PathVariable("userId") UUID userId,
            @PathVariable("articleId") UUID articleId,
            @PathVariable("commentId") UUID commentId) {

        UUID loginUserId = userId;

        CommentCommand.DeleteCommentCommand command = CommentCommand.DeleteCommentCommand.builder()
                .userId(loginUserId)
                .articleId(articleId)
                .commentId(commentId)
                .build();

        writeService.deleteComment(command);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(command, command.getCommentId() + "댓글 삭제 성공"));
    }

    @Getter
    @EqualsAndHashCode
    @NoArgsConstructor
    public static class RegisterCommentRequest {
        private String content;
    }
}
