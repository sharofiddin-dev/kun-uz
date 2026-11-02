package dasturlash.uz.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@JsonPropertyOrder({
        "orderNumber",
        "sectionKey",
        "nameUz",
        "nameRu",
        "nameEn",
        "createdDate"
})

@Getter
@Setter
public class SectionResponseDTO {
    private Integer id;
    private Integer orderNumber;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String sectionKey;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;
}
