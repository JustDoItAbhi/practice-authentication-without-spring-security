package authentication.authentication.authen.respositorys;


import authentication.authentication.authen.entity.Sessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Sessions, Integer> {
    Optional<Sessions> findByTokenAndCustomersId(String token, int CustomerId);
}
