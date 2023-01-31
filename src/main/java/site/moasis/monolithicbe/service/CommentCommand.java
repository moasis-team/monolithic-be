package site.moasis.monolithicbe.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import site.moasis.monolithicbe.common.exception.SelfValidating;

import java.util.UUID;

public class CommentCommand {

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class RegisterCommentCommand extends SelfValidating<RegisterCommentCommand> {

        @NotNull
        private UUID articleId;

        @NotBlank
        @Length(min =3, max = 1000)
        private String content;

        @Builder
        public RegisterCommentCommand(UUID articleId, String content) {
            this.articleId = articleId;
            this.content = content;
            validateSelf();
        }
    }
}
