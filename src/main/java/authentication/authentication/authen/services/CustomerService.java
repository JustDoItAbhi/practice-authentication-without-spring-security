package authentication.authentication.authen.services;


import authentication.authentication.authen.dtos.CustomersResponseDto;
import authentication.authentication.authen.dtos.authenticationdtos.LogOutDto;
import authentication.authentication.authen.dtos.authenticationdtos.LoginDtos;
import authentication.authentication.authen.dtos.authenticationdtos.SignUpDto;
import authentication.authentication.authen.entity.Customers;
import authentication.authentication.authen.entity.LogoutType;
import authentication.authentication.authen.entity.SessionType;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

public interface CustomerService {
    CustomersResponseDto signUp(SignUpDto dto);
    Pair<Customers, MultiValueMap<String, String>> login(LoginDtos dtos);
    Optional<CustomersResponseDto> validateToken(String token, int customerId);
    LogoutType logout(LogOutDto dto);
   boolean deleteById(int id);
   CustomersResponseDto getByid(int id);
   CustomersResponseDto findByEmail(String email);

}
