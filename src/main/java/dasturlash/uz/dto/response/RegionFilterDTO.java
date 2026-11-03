package dasturlash.uz.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegionFilterDTO {
    private Integer orderNumber;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String regionKey;

    private LocalDateTime createdDateFrom;
    private LocalDateTime createdDateTo;
}
