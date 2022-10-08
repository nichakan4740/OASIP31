package sit.int221.oasip.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int221.oasip.Entity.Roles;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddUserDTO {
    private int id;
    private String name;
    private String email;
    private Roles role;
    private String password;

}