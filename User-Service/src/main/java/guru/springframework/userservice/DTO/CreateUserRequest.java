package guru.springframework.userservice.DTO;

import lombok.Data;

@Data
public class CreateUserRequest {  // This is for creating new user

    // Add Validtion

    private String username;
    private String email;
    private String password;
    private String role;

}
