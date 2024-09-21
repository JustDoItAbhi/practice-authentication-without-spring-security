package authentication.authentication.authen.dtos.designation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequestDto {
    private int customerId;
    private String roleName;
}
