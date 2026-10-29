package dasturlash.uz.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ArticleRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Content is required")
    private String content;
    private UUID imageId;
    @NotNull(message = "Region id is required")
    private Integer regionId;
    @NotNull(message = "Category id is required")
    private Integer categoryId;
    @NotNull(message = "Moderator id is required")
    private Integer moderatorId;
    @NotEmpty(message = "At least one article type is required")
    private List<Integer> articleTypeIds;
}
