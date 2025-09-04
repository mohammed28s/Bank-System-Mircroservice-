package guru.springframework.userservice.ServiceInterface;

import guru.springframework.userservice.DTO.CreateUserRequest;
import guru.springframework.userservice.DTO.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserServiceInterface {

    UserDto createUser(CreateUserRequest request);  // creating new user

    List<UserDto> getAllUsers(); // fetching all user's data

    Optional<UserDto> getUserById(Integer id);

    Optional<UserDto> getUserByUsername(String username);



}
