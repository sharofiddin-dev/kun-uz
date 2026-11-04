package dasturlash.uz.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailVerificationDTO {

    @NotBlank(message = "Username cannot be null")
    private String username;

    @NotBlank(message = "Verification code cannot be null")
    @Pattern(regexp = "^[0-9]{6}$", message = "Code must contain 6 digits")
    private String code;
}