package guru.springframework.userservice.DTO;

import lombok.Data;

@Data
public class AuthRequest { // This is for login

    // Add Validation
    private String username;

    private String password;

}
