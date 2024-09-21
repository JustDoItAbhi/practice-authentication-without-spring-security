package authentication.authentication.authen.respositorys;


import authentication.authentication.authen.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRespository extends JpaRepository<Roles, Integer> {
    List<Roles> findByRoleName(String name);
}
