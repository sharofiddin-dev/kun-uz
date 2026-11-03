package dasturlash.uz.dto.response;

import dasturlash.uz.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ArticleResponseDTO {

    private UUID id;

    private String title;
    private String description;
    private String content;

    private String imageId;

    private Integer readTime;

    private Long sharedCount;
    private Long viewCount;

    private Integer regionId;

    private Integer moderatorId;
    private Integer publisherId;

    private List<Integer> categoryList;
    private List<Integer> sectionList;

    private ArticleStatus status;

    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;

    private Boolean visible;
}