package dasturlash.uz.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class SectionFilterDTO {
    private Integer orderNumber;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String sectionKey;

    private LocalDateTime createdDateFrom;
    private LocalDateTime createdDateTo;
}
