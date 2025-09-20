package guru.springframework.userservice.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
public class CreateUserRequest {  // This is for creating new user

    // Add Validtion

    @NotBlank(message = "Username is requeried")

    @Pattern(regexp = "^[a-zA-Z0-9_]{3,20}$",
            message = "Username must be 3-20 characters and " +
                    "can only contain letters," +
                    " numbers, and underscores")
    private String username;

    @NotBlank(message = "Email is requeried")
    @Email(message = "Must be a valid email address")
    private String email;

    @NotBlank(message = "Password is requeried")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, no spaces, and be at least 8 characters long"
    )
    private String password;

    @NotBlank(message = "Role is requeried")
    @Pattern(regexp = "USER|ADMIN|MODERATOR",
            message = "Role must be one of: USER, ADMIN, MODERATOR")
    private String role;

}
