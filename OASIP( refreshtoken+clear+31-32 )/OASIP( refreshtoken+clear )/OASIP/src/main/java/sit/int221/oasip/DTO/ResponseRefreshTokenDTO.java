package sit.int221.oasip.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseRefreshTokenDTO {
    private String message;
    private String refreshtoken;

    public ResponseRefreshTokenDTO(String message , String refreshtoken) {
        this.message = message;
        this.refreshtoken = refreshtoken;
    }

}
