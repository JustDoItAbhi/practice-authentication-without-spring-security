package authentication.authentication.authen.respositorys;



import authentication.authentication.authen.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRespository extends JpaRepository<Customers,Integer> {
   Optional<Customers> findByCustomersEmail(String email);

   }
