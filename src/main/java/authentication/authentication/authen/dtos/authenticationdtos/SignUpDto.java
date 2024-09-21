package authentication.authentication.authen.dtos.authenticationdtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignUpDto {
    private String name;
    private String email;
    private String password;
    private String designation;
}
