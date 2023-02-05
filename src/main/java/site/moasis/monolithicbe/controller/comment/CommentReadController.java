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
import site.moasis.monolithicbe.service.CommentWriteService;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/{userId}/articles/{articleId}") // todo: 스프링 시큐리티 기능 완성 후 유저 정보 가져올 수 있을 때 "/users/{userId}" 삭제
@Tag(name = "CommentController", description = "댓글 조회 기능")
public class CommentReadController {

    private final CommentReadService readService;
    private final CommentWriteService writeService;

    @GetMapping("/comments")
    @Operation(summary = "댓글 전체 조회")
    public ResponseEntity<CommonResponse<?>> findAll() {
        var commentInfo = readService.selectAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(commentInfo, "전체 댓글 조회 성공"));
    }

    @GetMapping("/comments/{commentId}")
    @Operation(summary = "댓글 단건 조회")
    public ResponseEntity<CommonResponse<?>> findOne(@PathVariable("commentId") UUID commentId) {
        var commentInfo = readService.selectOne(commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(commentInfo, "댓글 조회 성공"));
    }
}
