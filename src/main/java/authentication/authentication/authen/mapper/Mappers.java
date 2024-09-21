package authentication.authentication.authen.mapper;


import authentication.authentication.authen.dtos.CustomersResponseDto;
import authentication.authentication.authen.dtos.authenticationdtos.SignUpDto;
import authentication.authentication.authen.dtos.designation.RoleResponseDto;
import authentication.authentication.authen.entity.Customers;
import authentication.authentication.authen.entity.Roles;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;


public class Mappers {
    public static CustomersResponseDto from(Customers customers){
        CustomersResponseDto responseDto=new CustomersResponseDto();
        responseDto.setId(customers.getId());
        responseDto.setName(customers.getName());
        responseDto.setEmail(customers.getCustomersEmail());
        responseDto.setPassword(customers.getPassword());
//        List<Roles>roles=new ArrayList<>();
//        for(Roles roles1:customers.getRolesList()){
//            DesignationResponseDto designationResponseDto=new DesignationResponseDto();
//            designationResponseDto.setRoleId(roles1.getId());
//            designationResponseDto.setCustomerId(customers.getId());
//            designationResponseDto.setRoleName(roles1.getRoleName());
//            designationResponseDto.setCuName(customers.getName());
//
//        }

        return responseDto;
    }
    public static Customers from(SignUpDto dto){
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        Customers responseDto=new Customers();
        responseDto.setName(dto.getName());
//        responseDto.setCustomersEmail(dto.getEmail());
        responseDto.setPassword(passwordEncoder.encode(dto.getPassword()));
        responseDto.setPassword(dto.getPassword());


       // Set designations for the customer
        return responseDto;
    }

    public static RoleResponseDto fromrole(Roles roles){
        RoleResponseDto roleResponseDto =new RoleResponseDto();
        roles.setId(roles.getId());
        roleResponseDto.setCustomerId(roles.getId());
        roleResponseDto.setRoleName(roles.getRoleName());
        roleResponseDto.setRoleId(roles.getId());
        return roleResponseDto;
    }
    public static Roles fromrole(SignUpDto dto){
            Roles roles =new Roles();
            roles.setRoleName(dto.getDesignation());
      return roles;
    }

}
