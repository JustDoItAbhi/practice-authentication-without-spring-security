package authentication.authentication.authen.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Entity
@ToString
public class Customers extends Basemodels{
    private String name;
    private String customersEmail;
    private String password;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Roles> rolesList;
}
