package authentication.authentication.authen.services;

import authentication.authentication.authen.dtos.designation.RoleRequestDto;
import authentication.authentication.authen.dtos.designation.RoleResponseDto;
import authentication.authentication.authen.entity.Customers;
import authentication.authentication.authen.entity.Roles;
import authentication.authentication.authen.respositorys.CustomerRespository;
import authentication.authentication.authen.respositorys.RoleRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImplI implements iRoleService {
    @Autowired
    private RoleRespository roleRespository;
    @Autowired
    private CustomerRespository customerRespository;
    @Override
    public List<Roles> createRole(RoleRequestDto requestDto) {
        Optional<Customers>existingCustomer=customerRespository.findById(requestDto.getCustomerId());
        if(existingCustomer.isEmpty()){
         throw new RuntimeException("CUSTOMER IS NOT AVALAIABLE "+requestDto.getCustomerId());
    }
        Customers customers=existingCustomer.get();
        List<Roles>rolesList= roleRespository.findByRoleName(requestDto.getRoleName());
        Roles roles=new Roles();
       roles.setRoleName(requestDto.getRoleName());
      List<Customers>customers1=new ArrayList<>();
       for(Roles roles1:rolesList){
            customers1.add(roles1.getCustomers());
      }
        roles.setCustomers(customers);
        roleRespository.save(roles);
       return List.of(roles);

    }

    @Override
    public RoleResponseDto getRoleById(int id) {
        return null;
    }
}
