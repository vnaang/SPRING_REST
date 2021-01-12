package hello.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.Set;


@Entity
@Table(schema = "foo", name = "roles")
public class Role implements GrantedAuthority {

    @Id
    private Long id;

    @Column
    private String role;

    @Fetch(FetchMode.JOIN)
    @ManyToMany(mappedBy = "roles")
    private Set<User> user;

    public Role(Long id){
        this.id = id;
    }

    public Role(String role){
        this.role = role;
    }

    public Role(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Role() { }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public String toString() {
        return "" + role + "";
    }
}

