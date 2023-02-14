package site.moasis.monolithicbe.domain.article.service;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;
import site.moasis.monolithicbe.common.exception.SelfValidating;

import java.util.UUID;

public class ArticleCommand {


    public static class CreateCommand extends SelfValidating<CreateCommand> {

        public UUID userId;
        @NotBlank String title;
        @NotBlank
        @Length(min = 3, max = 10000) String content;

        @Builder
        public CreateCommand(String title, String content) {
            this.title = title;
            this.content = content;
            validateSelf();
        }
    }
}
