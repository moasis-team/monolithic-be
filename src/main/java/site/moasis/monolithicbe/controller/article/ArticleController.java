package site.moasis.monolithicbe.controller.article;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.moasis.monolithicbe.controller.common.CommonResponse;
import site.moasis.monolithicbe.domain.article.dto.ArticleDto;
import site.moasis.monolithicbe.domain.article.dto.ArticleRequest;
import site.moasis.monolithicbe.domain.article.service.ArticleReadService;
import site.moasis.monolithicbe.domain.article.service.ArticleWriteService;

import java.util.UUID;

import static site.moasis.monolithicbe.domain.article.dto.ArticleDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")

@Tag(name = "ArticleController", description = "게시글을 각 속성 값으로 조회 하고 생성, 삭제 가능하다")
public class ArticleController {

    private final ArticleReadService articleReadService;
    private final ArticleWriteService articleWriteService;

    @GetMapping("/articles")
    @Operation(summary = "게시글 전체 조회")
    public ResponseEntity<CommonResponse<?>> findAll() {
        var articleInfo = articleReadService.searchAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(articleInfo, "게시글 검색 결과"));
    }

    @GetMapping("/articles/{articleId}")
    @Operation(summary = "article_id로 게시글 단건 조회")
    public ResponseEntity<CommonResponse<?>> searchOne(@PathVariable UUID articleId) {
        var articleInfo = articleReadService.searchOne(articleId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(articleInfo, "게시글 검색 결과"));
    }

    @GetMapping("/articles/{userId}")
    @Operation(summary = "user_id로 게시글 조회")
    public ResponseEntity<CommonResponse<?>> searchByUserId(@PathVariable UUID userId){
        var articleInfo = articleReadService.searchByUserId(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(articleInfo, userId + "검색 결과"));
    }

    @GetMapping("/articles/{title}")
    @Operation(summary = "제목으로 게시글 조회")
    public ResponseEntity<CommonResponse<?>> searchByTitle(@PathVariable String title) {
        var articleInfo = articleReadService.searchBytitle(title);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(articleInfo, title + "검색 결과"));
    }

    @GetMapping("/articles/{content}")
    @Operation(summary = "내용으로 게시글 조회")
    public ResponseEntity<CommonResponse<?>> searchByContent(@PathVariable String content) {
        var articleInfo = articleReadService.searchBycontent(content);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(articleInfo, content + "검색 결과"));
    }

    @PostMapping("/users/{userId}/articles/{articleId}")
    @Operation(summary = "게시글 생성")
    public ResponseEntity<CommonResponse<?>> createArticle(articleCreateDto dto){
        articleWriteService.createArticle(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.success("게시글 생성 완료"));
    }

    @PostMapping("/users/{userId}/articles/{articleId}/update")
    @Operation(summary = "게시글 업데이트")
    public ResponseEntity<CommonResponse<String>> updateArticle(
            @PathVariable UUID articleId,
            @PathVariable UUID userId,
            ArticleRequest articleRequest
    )    {
        articleWriteService.updateArticle(userId,articleId, articleRequest);

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.success("게시글 업데이트 완료"));
    }

    @PostMapping("/users/{userId}/articles/{articleId}/delete")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<CommonResponse<?>> deleteArticle(@PathVariable UUID articleId){
        ArticleWriteService.deleteOne(articleId);
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.success("게시글 삭제 완료"));
    }

}
