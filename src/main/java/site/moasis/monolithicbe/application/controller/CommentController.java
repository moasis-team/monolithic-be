package site.moasis.monolithicbe.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.moasis.monolithicbe.common.response.CommonResponse;
import site.moasis.monolithicbe.domain.comment.service.CommentCommand;
import site.moasis.monolithicbe.domain.comment.service.CommentInfo;
import site.moasis.monolithicbe.domain.comment.service.CommentReadService;
import site.moasis.monolithicbe.domain.comment.service.CommentWriteService;

import java.util.UUID;

import static site.moasis.monolithicbe.domain.comment.dto.CommentDto.CommentCreateDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Tag(name = "CommentController", description = "댓글을 각 속성 값으로 조회 하고 생성, 삭제 가능하다")
public class CommentController {

    private final CommentReadService readService;
    private final CommentWriteService writeService;

    @GetMapping
    @Operation(summary = "댓글 전체 조회")
    public ResponseEntity<CommonResponse<?>> findAll(){
        var commentInfo = readService.selectAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(commentInfo, "전체 댓글 조회 성공"));
    }

    @GetMapping("/{commentId}")
    @Operation(summary = "댓글 단건 조회")
    public ResponseEntity<CommonResponse<?>> findOne(@PathVariable("commentId") UUID commentId){
        var commentInfo = readService.selectOne(commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(commentInfo, "댓글 조회 성공"));
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "user_id를 통한 댓글 조회")
    public ResponseEntity<CommonResponse<?>> findByUser(@PathVariable Long userId){
        var commentInfo = readService.selectByUser(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(commentInfo, "Id가 " + userId + "인 유저가 작성한 댓글 조회 성공"));
    }

    @GetMapping("/articles/{articleId}")
    @Operation(summary = "article_id를 통한 댓글 조회")
    public ResponseEntity<CommonResponse<?>> findByArticle(@PathVariable Long articleId){
        var commentInfo = readService.selectByArticle(articleId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(commentInfo, articleId + "번 게시글 댓글 조회 성공"));
    }

    @PostMapping("/users/{userId}/articles/{articleId}/comments")
    @Operation(summary = "댓글 생성")
    public ResponseEntity<CommonResponse<CommentInfo>> registerComment(
            @PathVariable UUID userId,
            @PathVariable UUID articleId,
            @RequestBody RegisterCommentRequest registerCommentRequest){

        UUID loginUserId = userId;

        var command = CommentCommand.RegisterCommentCommand
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

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterCommentRequest {
        private String content;
    }

    @DeleteMapping("/id/{commentId}")
    @Operation(summary = "id를 통한 댓글 삭제")
    public ResponseEntity<CommonResponse<?>> deleteOne(@PathVariable("commentId") UUID commentId){
        var commentInfo = writeService.dropOne(commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(null, commentInfo + "번 댓글 삭제 완료"));
    }

//    @PostMapping("/user/{userId}")
//    @Operation(summary = "해당 user가 작성한 댓글 삭제")
//    public ResponseEntity<CommonResponse<?>> deleteByUser(@PathVariable Long userId){
//        var commentInfo = writeService.dropByUser(userId);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(CommonResponse.success(null, "Id가 " + commentInfo + "인 유저가 작성한 댓글 삭제 성공"));
//    }

//    @PostMapping("/article/{articleId}")
//    @Operation(summary = "해당 article에 속한 댓글 삭제")
//    public ResponseEntity<CommonResponse<?>> deleteByArticle(@PathVariable Long articleId){
//        var articleInfo = writeService.dropByArticle(articleId);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(CommonResponse.success(null, articleInfo + "번 게시물의 댓글 삭제 성공"));
//    }
}
