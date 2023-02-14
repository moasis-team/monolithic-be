package site.moasis.monolithicbe.controller.article;


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
import site.moasis.monolithicbe.domain.article.service.ArticleReadService;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")

@Tag(name = "ArticleReadController", description = "게시글을 각 속성 값으로 조회 가능하다")
public class ArticleReadController {

    private final ArticleReadService articleReadService;

    @GetMapping("/articles")
    @Operation(summary = "게시글 전체 조회")
    public ResponseEntity<CommonResponse<?>> searchAll() {
        var articleInfo = articleReadService.searchArticles();
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

    @GetMapping("/articles/userId/{userId}")
    @Operation(summary = "user_id로 게시글 조회")
    public ResponseEntity<CommonResponse<?>> searchByUserId(@PathVariable UUID userId) {
        var articleInfo = articleReadService.searchByUserId(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(articleInfo, userId + "검색 결과"));
    }

    @GetMapping("/articles/title/{title}")
    @Operation(summary = "제목으로 게시글 조회")
    public ResponseEntity<CommonResponse<?>> searchByTitle(@PathVariable String title) {
        var articleInfo = articleReadService.searchBytitle(title);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(articleInfo, title + "검색 결과"));
    }

    @GetMapping("/articles/content/{content}")
    @Operation(summary = "내용으로 게시글 조회")
    public ResponseEntity<CommonResponse<?>> searchByContent(@PathVariable String content) {
        var articleInfo = articleReadService.searchBycontent(content);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(articleInfo, content + "검색 결과"));
    }

}
