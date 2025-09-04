package guru.springframework.userservice.DTO;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {  // This is for JWT token

    @JsonIgnore
    private String token;

}
