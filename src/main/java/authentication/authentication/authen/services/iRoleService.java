package authentication.authentication.authen.services;

import authentication.authentication.authen.dtos.designation.RoleRequestDto;
import authentication.authentication.authen.dtos.designation.RoleResponseDto;
import authentication.authentication.authen.entity.Roles;

import java.util.List;

public interface iRoleService {
    List<Roles> createRole(RoleRequestDto requestDto);
    RoleResponseDto getRoleById(int id);
}
