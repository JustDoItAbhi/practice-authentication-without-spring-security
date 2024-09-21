package authentication.authentication.authen.services;
import authentication.authentication.authen.dtos.CustomersResponseDto;
import authentication.authentication.authen.dtos.authenticationdtos.LogOutDto;
import authentication.authentication.authen.dtos.authenticationdtos.LoginDtos;
import authentication.authentication.authen.dtos.authenticationdtos.SignUpDto;
import authentication.authentication.authen.entity.*;
import authentication.authentication.authen.mapper.Mappers;
import authentication.authentication.authen.respositorys.CustomerRespository;
import authentication.authentication.authen.respositorys.RoleRespository;
import authentication.authentication.authen.respositorys.SessionRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.security.KeyPair;
import java.util.*;

@Service
public class CustomersServiceImpl implements CustomerService {
    @Autowired
    private CustomerRespository customerRespository;
    @Autowired
    private RoleRespository roleRespository;
    @Autowired
    private SessionRepository sessionRepository;
@Autowired
private PasswordEncoder passwordEncoder;
    @Override
    public CustomersResponseDto signUp(SignUpDto dto) {
        Optional<Customers>existingCustomer = customerRespository.findByCustomersEmail(dto.getEmail());
        if (existingCustomer.isPresent()){
            throw new RuntimeException ("CUSTOMER EMAIL ALREADY EXISTS "+dto.getEmail());
        }
        Customers newCustomer = new Customers();
        newCustomer.setCustomersEmail(dto.getEmail());
        newCustomer.setName(dto.getName());  // Assuming you have name in SignUpDto
        newCustomer.setPassword(passwordEncoder.encode(dto.getPassword()));  // Save password (ideally, hash it)
        newCustomer.setPassword(passwordEncoder.encode(dto.getPassword()));
        // Save the new customer to the repository
        customerRespository.save(newCustomer);

        return Mappers.from(newCustomer);
    }

    @Override
    public Pair<Customers, MultiValueMap<String, String>> login(LoginDtos dtos) {
        Optional<Customers>customers1 = customerRespository.findByCustomersEmail(dtos.getEmail());
        Customers customers=customers1.get();
        if (!customers1.isPresent()) {
            throw new RuntimeException("CUSTOMER NOT EXITS :: " + dtos.getEmail());
        }

 if (!passwordEncoder.matches(dtos.getPassword(), customers.getPassword() )) {
     throw new RuntimeException("INVALID PASSWORD " + dtos.getPassword());
 }


        CustomersResponseDto customersResponseDto=Mappers.from(customers);
        Map<String,Object>claims=new HashMap<>();
        claims.put("Claim_id__",customersResponseDto.getId());
        claims.put("Claim_email__",customersResponseDto.getEmail());
        claims.put("Claim_name__",customersResponseDto.getName());
        claims.put("claims_phone_",customersResponseDto.getRoles());
        KeyPair keyPair= Keys.keyPairFor(SignatureAlgorithm.ES256);

        String Token= Jwts.builder()
                .addClaims(claims)
                .signWith(keyPair.getPrivate(),SignatureAlgorithm.ES256)
                .compact();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("AUTH_TOKEN", Token);
        headers.add("CUSTOMER_ID",customersResponseDto.getName());

        Pair<Customers, MultiValueMap<String, String>> p = new Pair<>(customers, headers);
        customers.setCustomersEmail(dtos.getEmail());
        Sessions sessions = new Sessions();
        sessions.setSessionType(SessionType.ACTIVE);
        sessions.setCustomers(customers);
        sessions.setToken( Token);
//            sessions.setExpiryDate(new Date(2024, 10, 25));
        sessionRepository.save(sessions);
        customers.setPassword(Token);
        return p;
    }

    @Override
    public Optional<CustomersResponseDto> validateToken(String token,int customerId) {
        Optional<Sessions>existingSession=sessionRepository.findByTokenAndCustomersId(token,customerId);
        Sessions sessions=existingSession.get();
        if (existingSession.isEmpty()){
            return Optional.empty();
        }
        Sessions sessions1=existingSession.get();

        if ( !sessions1.getSessionType().equals(SessionType.ACTIVE)) {
            return Optional.empty();
        }
        Customers customers=customerRespository.findById(customerId).orElseThrow
                (()-> new RuntimeException("User id not valid :: "+customerId));
        CustomersResponseDto customersResponseDto=Mappers.from(customers);

        return Optional.of(customersResponseDto);
    }

    @Override
    public LogoutType logout(LogOutDto dto) {
        Optional<Customers>customers = customerRespository.findByCustomersEmail(dto.getEmail());
        if (!customers.isPresent()) {
            throw new RuntimeException("CHECK YOUR EMAIL AND LOGIN DETAILS " + dto.getEmail());
        }
        Customers customers1=customers.get();
            Sessions sessions = new Sessions();
            sessions.setCustomers(customers1);
            sessions.setSessionType(SessionType.NOT_VALID);
            sessions.setExpiryDate(null);
            sessionRepository.save(sessions);
            return LogoutType.LOGOUT;
    }

    @Override
    public boolean deleteById(int id) {
        // Check if the customer exists
        Optional<Customers> customerOpt = customerRespository.findById(id);
        Customers customers=customerOpt.get();
        if (customers.getCustomersEmail().equals(customers.getCustomersEmail())) {
            // Perform the deletion
            customerRespository.deleteById(id);
            return true;
        } else {
            // Customer not found
            return false;
        }
    }

    @Override
    public CustomersResponseDto getByid(int id) {
        Optional<Customers>exitingCustomer=customerRespository.findById(id);
        if (exitingCustomer.isEmpty()){
            throw new RuntimeException(" NO SUCH CUSTOMER EXISTS "+ id);
        }
        Customers customers=exitingCustomer.get();
        return Mappers.from(customers);
    }

    @Override
    public CustomersResponseDto findByEmail(String email) {
        Optional<Customers> customers=customerRespository.findByCustomersEmail(email);
        if(!customers.isPresent()){
            throw new RuntimeException("EMAIL NOT VALID "+ email);
        }
        Customers customers1=customers.get();
        return Mappers.from(customers1);
    }
}
