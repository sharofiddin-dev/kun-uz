package dasturlash.uz.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleSectionRequestDTO {

    @NotNull
    private Integer id;
}