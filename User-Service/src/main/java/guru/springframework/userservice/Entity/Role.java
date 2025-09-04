package guru.springframework.userservice.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;   // ADMIN or CUSTOMER

    @ManyToMany(mappedBy = "role")
    private Set<Users> users;
}
