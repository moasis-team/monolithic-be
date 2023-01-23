package site.moasis.monolithicbe.domain.comment.service;

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
        private UUID userId;

        @NotNull
        private UUID articleId;

        @NotBlank
        @Length(max = 1000)
        private String content;

        @Builder
        public RegisterCommentCommand(UUID userId, UUID articleId, String content) {
            this.userId = userId;
            this.articleId = articleId;
            this.content = content;
            validateSelf();
        }
    }
}
