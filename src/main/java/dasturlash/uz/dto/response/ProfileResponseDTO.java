package dasturlash.uz.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@JsonPropertyOrder({
        "id",
        "name",
        "surname",
        "phone",
        "email",
        "password",
        "role",
        "status",
        "visible",
        "createdDate"
})

@Getter
@Setter
public class ProfileResponseDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private ProfileStatus status;
    private ProfileRole role;
    private Boolean visible;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;
}
