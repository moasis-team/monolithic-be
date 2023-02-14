package site.moasis.monolithicbe.domain.article.service;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import site.moasis.monolithicbe.domain.article.entity.Article;

public interface ArticleInfoMapper {

    ArticleInfoMapper INSTANCE = Mappers.getMapper(ArticleInfoMapper.class);

    @Mapping(target = "articleId", source = "article.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "createdAt", source = "article.createdAt")
    ArticleInfo toArticleInfo(Article article);
}
