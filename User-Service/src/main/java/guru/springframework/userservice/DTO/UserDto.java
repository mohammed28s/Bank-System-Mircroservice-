package guru.springframework.userservice.DTO;

import lombok.Data;
import java.util.Set;

@Data
public class UserDto {  // This is to fetch user information

    private  Integer id;
    private String username;
    private  String email;
    private Set<String> roles; // one user can many roles

}
