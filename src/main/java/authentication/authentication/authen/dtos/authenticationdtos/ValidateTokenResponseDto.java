package authentication.authentication.authen.dtos.authenticationdtos;

import authentication.authentication.authen.dtos.CustomersResponseDto;
import authentication.authentication.authen.entity.SessionType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidateTokenResponseDto {
private CustomersResponseDto customersResponseDto;
private SessionType sessionType;
}
