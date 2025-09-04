package guru.springframework.userservice.Repository;

import guru.springframework.userservice.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);  // This method for finding User info by the username
}
