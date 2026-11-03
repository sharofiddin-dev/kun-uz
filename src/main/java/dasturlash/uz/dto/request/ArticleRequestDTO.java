package dasturlash.uz.dto.request;

import dasturlash.uz.dto.request.ArticleCategoryRequestDTO;
import dasturlash.uz.dto.request.ArticleSectionRequestDTO;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotBlank
    private String content;

    private String imageId;

    private Integer regionId;

    @NotNull
    @Min(1)
    private Integer readTime;

    private List<ArticleCategoryRequestDTO> categoryList;

    private List<ArticleSectionRequestDTO> sectionList;
}