package authentication.authentication.authen.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Roles extends Basemodels{
    @ManyToOne
    private Customers customers;
    private String roleName;
}
