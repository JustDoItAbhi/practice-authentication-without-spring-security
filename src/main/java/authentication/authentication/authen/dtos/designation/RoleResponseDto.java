package authentication.authentication.authen.dtos.designation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponseDto {
    private int RoleId;
    private int customerId;
    private String roleName;
    private String cuName;
    private String cuEmail;
}
