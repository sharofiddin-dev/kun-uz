package dasturlash.uz.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleTypeRequestDTO {
    @NotNull(message = "Order number is required")
    @Min(1)
    private Integer orderNumber;
    @NotBlank(message = "Uzbek name is required")
    private String nameUz;
    @NotBlank(message = "Russian name is required")
    private String nameRu;
    @NotBlank(message = "English name is required")
    private String nameEn;
}
