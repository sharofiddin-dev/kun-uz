package dasturlash.uz.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import dasturlash.uz.enums.Language;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonPropertyOrder({
        "id",
        "order_number",
        "nameUz",
        "nameRu",
        "nameEn",
        "visible",
        "createdDate"
})
@Getter
@Setter
public class ArticleTypeResponseDTO {
    private Integer id;
    private Integer orderNumber;
    private Language nameUz;
    private Language nameRu;
    private Language nameEn;
    private Boolean visible;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;
}
