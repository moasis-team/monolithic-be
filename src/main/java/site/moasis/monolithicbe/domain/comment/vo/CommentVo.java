package site.moasis.monolithicbe.domain.comment.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentVo {

    @Getter
    @EqualsAndHashCode
    @NoArgsConstructor
    public static class RegisterCommentRequest {
        private String content;

        public void blindDescription(String content) {
            this.content = content;
        }
    }
}
