package authentication.authentication.authen.controller;
import authentication.authentication.authen.dtos.CustomersResponseDto;
import authentication.authentication.authen.dtos.authenticationdtos.*;
import authentication.authentication.authen.entity.Customers;
import authentication.authentication.authen.entity.LogoutType;
import authentication.authentication.authen.entity.SessionType;
import authentication.authentication.authen.mapper.Mappers;
import authentication.authentication.authen.services.CustomerService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {
@Autowired
    public CustomerService customerService;
@PostMapping("/signup")
    public ResponseEntity<CustomersResponseDto> SignUp(@RequestBody SignUpDto dto){
    CustomersResponseDto customersResponseDto=customerService.signUp(dto);
    return new ResponseEntity<>(customersResponseDto, HttpStatus.CREATED);
}

    @PostMapping("/login")
    public ResponseEntity<CustomersResponseDto> login(@RequestBody  LoginDtos dtos) {
        try {
            Pair<Customers, MultiValueMap<String, String>> userWithHeaders =
                    customerService.login(dtos);
            if (userWithHeaders.a == null) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(Mappers.from(userWithHeaders.a),
                    userWithHeaders.b, HttpStatus.OK);
        }catch (InvalidCredentialsException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }
    @GetMapping("/logout")
    public ResponseEntity<LogoutType> logout(@RequestBody LogOutDto dto){
    return ResponseEntity.ok( customerService.logout(dto));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteId(@PathVariable ("id")int id) {
        boolean isDeleted = customerService.deleteById(id);
        if (isDeleted) {
            // Return HTTP 200 OK status with no content
            return ResponseEntity.ok().build();
        } else {
            // Return HTTP 404 Not Found if the customer does not exist
            return ResponseEntity.notFound().build();
        }
    }
   @GetMapping("/validate")
        public ResponseEntity<ValidateTokenResponseDto> VALIDATE(@RequestBody ValidateRequestDto dto){
       Optional<CustomersResponseDto>customersResponseDto =customerService.validateToken(dto.getToken(),dto.getId());
       if (customersResponseDto.isEmpty()){
           ValidateTokenResponseDto validateTokenResponseDto =new ValidateTokenResponseDto();
           validateTokenResponseDto.setSessionType(SessionType.NOT_VALID);
           return new ResponseEntity<>(validateTokenResponseDto,HttpStatus.OK);
       }
       ValidateTokenResponseDto validateTokenResponseDto =new ValidateTokenResponseDto();
       validateTokenResponseDto.setSessionType(SessionType.ACTIVE);
       validateTokenResponseDto.setCustomersResponseDto(customersResponseDto.get());
       return new ResponseEntity<>(validateTokenResponseDto,HttpStatus.OK);
        }
//        @GetMapping("/{id}")
//    public ResponseEntity<CustomersResponseDto> getbYiD(@PathVariable ("id")int id){
//    return ResponseEntity.ok(customerService.getByid(id));
//        }

    @GetMapping("/{email}")
    public ResponseEntity getbYiD(@PathVariable ("email")String email){
        return ResponseEntity.ok(customerService.findByEmail(email));

    }


}
