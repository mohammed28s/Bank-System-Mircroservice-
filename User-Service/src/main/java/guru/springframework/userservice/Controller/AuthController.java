package guru.springframework.userservice.Controller;


import guru.springframework.userservice.DTO.AuthRequest;
import guru.springframework.userservice.DTO.AuthResponse;
import guru.springframework.userservice.DTO.CreateUserRequest;
import guru.springframework.userservice.DTO.UserDto;
import guru.springframework.userservice.Entity.Role;
import guru.springframework.userservice.Entity.Users;
import guru.springframework.userservice.Repository.RoleRepository;
import guru.springframework.userservice.Repository.UserRepository;
import guru.springframework.userservice.Security.JwtUtil;
import guru.springframework.userservice.ServiceImpl.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")  // This is main path to the auth controller
public class AuthController {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UserService userService;

    public AuthController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }


    @PostMapping("/register") // This is register API
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserRequest req) {
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already exists!");
        }

        Role role = roleRepository.findByName(req.getRole()!= null ? req.getRole() : "CUSTOMER")
                .orElseGet(() -> roleRepository.findByName("CUSTOMER").orElseThrow());

        Users users = new Users();
        users.setUsername(req.getUsername());
        users.setEmail(req.getEmail());
        users.setPassword_hash(passwordEncoder.encode(req.getPassword()));
        userRepository.save(users);

        UserDto dto = userService.getUserByUsername(users.getUsername()).orElse(null);
        return ResponseEntity.ok(dto);
    }


    @PostMapping("/login")  // This is login API
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())

            );

            var userDetails = userService.getUserByUsername(authRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtUtil.generateToken(new User(
                            userDetails.getUsername(),
                            "N/A",
                            java.util.Collections.emptyList()
                    )
            );
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }



}
