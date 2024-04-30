package alessia.repositories;

import alessia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersDAO extends JpaRepository <User, UUID> {
    Optional<User> findUserByemail(String email);
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserById(UUID id);

}
