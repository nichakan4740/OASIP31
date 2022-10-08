package sit.int221.oasip.DTO;

import lombok.Getter;
import lombok.Setter;
import sit.int221.oasip.Entity.Roles;

@Setter
@Getter
public class ResponseCreateTokenDTO {
    private String message;
    private String username;
    private Roles role;
    private String accessToken;
    private String refreshToken;

    public ResponseCreateTokenDTO(String message, String username , Roles role , String accessToken , String refreshToken) {
        this.message = message;
        this.username = username;
        this.role = role;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
