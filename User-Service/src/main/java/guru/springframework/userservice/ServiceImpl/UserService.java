package guru.springframework.userservice.ServiceImpl;

import guru.springframework.userservice.DTO.CreateUserRequest;
import guru.springframework.userservice.DTO.UserDto;
import guru.springframework.userservice.Entity.Role;
import guru.springframework.userservice.Entity.Users;
import guru.springframework.userservice.Repository.RoleRepository;
import guru.springframework.userservice.Repository.UserRepository;
import guru.springframework.userservice.ServiceInterface.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepo, RoleRepository roleRepository) {
        this.userRepository = userRepo;
        this.roleRepository = roleRepository;
    }


    @Override
    public UserDto createUser(CreateUserRequest request) {
        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword_hash(request.getPassword());
        user.setRole(Set.of(role));

        Users saved = userRepository.save(user);
        return mapToDto(saved);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> getUserById(Integer id) {
        return userRepository.findById(id).map(this::mapToDto);
    }

    @Override
    public Optional<UserDto> getUserByUsername(String username) {
        return userRepository.findByUsername(username).map(this::mapToDto);
    }


    private  UserDto mapToDto(Users user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRole().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));
        return dto;

    }
}
