package site.moasis.monolithicbe.domain.article.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.BusinessException;
import site.moasis.monolithicbe.common.exception.ErrorCode;
import site.moasis.monolithicbe.domain.article.dto.ArticleRequest;
import site.moasis.monolithicbe.domain.article.entity.Article;
import site.moasis.monolithicbe.domain.article.repository.ArticleRepository;
import site.moasis.monolithicbe.domain.article.service.ArticleCommand.CreateCommand;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleWriteService {

    private final ArticleRepository articleRepository;


    public Article createArticle(CreateCommand createCommand) {
        var article = Article.builder()
                .userId(createCommand.userId)
                .title(createCommand.title)
                .content(createCommand.content)
                .build();
        return articleRepository.save(article);

    }

    public UUID deleteOne(UUID articleId) {
        articleRepository.findById(articleId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND,
                "article.byId", List.of(articleId.toString())));
        articleRepository.deleteById(articleId);
        return articleId;
    }

    public void updateArticle(UUID articleId, ArticleRequest articleRequest) {
        var article = articleRepository.findById(articleId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND,
                "article.byId", List.of(articleId.toString())));
        article.setTitle(articleRequest.title());
        article.setContent(articleRequest.content());
    }

}



