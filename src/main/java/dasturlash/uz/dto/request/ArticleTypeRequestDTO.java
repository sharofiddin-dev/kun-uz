package dasturlash.uz.dto.request;

import dasturlash.uz.enums.Language;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleTypeRequestDTO {
    @NotNull(message = "Order number is required")
    @Min(1)
    private Integer orderNumber;
    @NotNull(message = "Language is required")
    private Language nameUz;
    @NotNull(message = "Language is required")
    private Language nameRu;
    @NotNull(message = "Language is required")
    private Language nameEn;
}
