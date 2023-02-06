package site.moasis.monolithicbe.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class CommentInfo {
    private UUID commentId;
    private String content;
    private LocalDateTime createdAt;
    private Boolean isDeleted;
    private String userName;

    public void blindDescription(String content) {
        this.content = content;
    }
}