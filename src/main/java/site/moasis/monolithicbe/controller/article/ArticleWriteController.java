package site.moasis.monolithicbe.controller.article;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.moasis.monolithicbe.controller.common.CommonResponse;
import site.moasis.monolithicbe.domain.article.dto.ArticleRequest;
import site.moasis.monolithicbe.domain.article.service.ArticleWriteService;

import java.util.UUID;

import static site.moasis.monolithicbe.domain.article.service.ArticleCommand.CreateCommand;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")

@Tag(name = "ArticleWriteController", description = "게시글을 각 속성 값으로 생성, 삭제, 수정 가능하다")
public class ArticleWriteController {

    private final ArticleWriteService articleWriteService;

    @PostMapping("/article")
    @Operation(summary = "게시글 생성")
    public ResponseEntity<CommonResponse<?>> createArticle(
            @RequestBody ArticleRequest articleRequest
    ) {
        var command = CreateCommand
                .builder()
                .title(articleRequest.title())
                .content(articleRequest.content())
                .build();
        var articleInfo = articleWriteService.createArticle(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.success(articleInfo, "게시글 생성 완료"));
    }

    @PostMapping("/article/{articleId}")
    @Operation(summary = "게시글 업데이트")
    public ResponseEntity<CommonResponse<String>> updateArticle(
            @PathVariable UUID articleId,
            @RequestBody ArticleRequest articleRequest
    ) {
        articleWriteService.updateArticle(articleId, articleRequest);
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.success("게시글 업데이트 완료"));
    }

    @DeleteMapping("/article/{articleId}")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<CommonResponse<?>> deleteArticle(@PathVariable UUID articleId) {
        articleWriteService.deleteOne(articleId);
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.success("게시글 삭제 완료"));
    }
}
