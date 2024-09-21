package authentication.authentication.authen.controller;

import authentication.authentication.authen.dtos.designation.RoleRequestDto;
import authentication.authentication.authen.services.iRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RolesController {
@Autowired
    private iRoleService designationService;
@PostMapping("/")
    public ResponseEntity createRole(@RequestBody RoleRequestDto requestDto){
    return ResponseEntity.ok(designationService.createRole(requestDto));
}
}
