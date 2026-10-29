package dasturlash.uz.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CommentResponseDTO {
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Integer profileId;
    private String profileName;
    private String profileSurname;
    private String content;
    private UUID articleId;
    private String articleTitle;
    private Long replyId;
    private Boolean visible;
    private Long likeCount;
}
