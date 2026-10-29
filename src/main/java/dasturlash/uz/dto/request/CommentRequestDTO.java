package dasturlash.uz.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CommentRequestDTO {
    @NotNull(message = "Profile id is required")
    private Integer profileId;
    @NotBlank(message = "Content is required")
    private String content;
    @NotNull(message = "Article id is required")
    private UUID articleId;
    private Long replyId;
}
