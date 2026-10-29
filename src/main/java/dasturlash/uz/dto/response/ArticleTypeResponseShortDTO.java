package dasturlash.uz.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({
        "orderNumber",
        "nameUz",
        "nameRu",
        "nameEn",
})
@Getter
@Setter
public class ArticleTypeResponseShortDTO {
    private Integer orderNumber;
    private String nameUz;
    private String nameRu;
    private String nameEn;

}
