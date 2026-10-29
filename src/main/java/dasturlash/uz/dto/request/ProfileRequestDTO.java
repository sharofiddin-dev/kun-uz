package dasturlash.uz.dto.request;

import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProfileRequestDTO {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 20, message = "Name must be between 3 and 20 characters")
    private String name;

    @NotBlank(message = "Surname is required")
    @Size(min = 3   , max = 20, message = "Surname must be between 3 and 20 characters")
    private String surname;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\+998[0-9]{9}$", message = "Phone format is invalid")
    private String phone;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "Password must contain uppercase, lowercase and digit"
    )
    private String password;

    private ProfileStatus status;
    private ProfileRole role;
    private Boolean visible;
    private UUID photoId;
}
