package authentication.authentication.authen.dtos;

import authentication.authentication.authen.entity.Roles;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CustomersResponseDto {
    private int id;
    private String name;
    private String email;
    private String password;
    private List<Roles> roles;
}
