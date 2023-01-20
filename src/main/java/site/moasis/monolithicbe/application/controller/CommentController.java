package site.moasis.monolithicbe.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.moasis.monolithicbe.common.response.CommonResponse;
import site.moasis.monolithicbe.domain.comment.dto.CommentDto;
import site.moasis.monolithicbe.domain.comment.service.CommentReadService;
import site.moasis.monolithicbe.domain.comment.service.CommentWriteService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@Tag(name = "CommentController", description = "댓글을 각 속성 값으로 조회 하고 생성, 삭제 가능하다")
public class CommentController {

    private final CommentReadService readService;
    private final CommentWriteService writeService;

    @GetMapping("/id/{commentId}")
    @Operation(summary = "id를 통한 댓글 단건 조회")
    public CommonResponse<?> retrieveComment(@PathVariable Long commentId){
        return CommonResponse.success(readService.retrieveCommentById(commentId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "user_id를 통한 댓글 조회")
    public CommonResponse<?> retrieveCommentsByUserId(@PathVariable Long userId){
        return CommonResponse.success(readService.retrieveCommentByUserId(userId));
    }

    @GetMapping("/article/{articleId}")
    @Operation(summary = "article_id를 통한 댓글 조회")
    public CommonResponse<?> retrieveCommentsByArticleId(@PathVariable Long articleId){
        return CommonResponse.success(readService.retrieveCommentByArticleId(articleId));
    }

    @PostMapping("/")
    @Operation(summary = "댓글 생성")
    public CommonResponse<?> createComment(CommentDto.commentCreateDto commentCreateDto){
        writeService.createComment(commentCreateDto);
        return CommonResponse.success("댓글 생성 완료");
    }

    @PostMapping("/id/{commentId}")
    @Operation(summary = "id를 통한 댓글 삭제")
    public CommonResponse<?> deleteComment(@PathVariable Long commentId){
        writeService.deleteComment(commentId);
        return CommonResponse.success("댓글 삭제 완료");
    }
}
