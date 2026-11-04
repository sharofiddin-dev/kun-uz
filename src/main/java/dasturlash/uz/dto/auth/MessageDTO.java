package dasturlash.uz.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    private String toAccount;
    private String subject; // title
    private String text; // content
}
