package site.moasis.monolithicbe.domain.article.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.domain.article.dto.ArticleRequest;
import site.moasis.monolithicbe.domain.article.entity.Article;
import site.moasis.monolithicbe.domain.article.repository.ArticleRepository;

import java.util.UUID;

import static site.moasis.monolithicbe.domain.article.dto.ArticleDto.*;
import static site.moasis.monolithicbe.domain.article.entity.Article.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleWriteService {

    private static ArticleRepository articleRepository;


    public void createArticle(articleCreateDto articleCreateDto) {
        Article articleEntity = builder()
                .content(articleCreateDto.content())
                .articleId(articleCreateDto.articleId())
                .userId(articleCreateDto.userId())
                .build();
        articleRepository.save(articleEntity);
    }

    public static UUID deleteOne(UUID articleId) {
        articleRepository.findById(articleId).orElseThrow(EntityNotFoundException::new);
        articleRepository.deleteById(articleId);
        return articleId;
    }

    public void updateArticle(UUID userId,UUID articleId, ArticleRequest articleRequest) {
        articleRepository.findById(userId);
    }
}


