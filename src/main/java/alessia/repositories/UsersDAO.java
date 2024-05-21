package alessia.repositories;

import alessia.entities.Activity;
import alessia.entities.Location;
import alessia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersDAO extends JpaRepository <User, UUID> {
    Optional<User> findUserByemail(String email);
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserById(UUID id);

    @Query("SELECT a FROM Activity a JOIN a.users u WHERE u.id = :userId")
    Optional<List<Activity>> getActivitiesByUserId(@Param("userId") UUID userId);

    @Query("SELECT l FROM Location l JOIN l.users u WHERE u.id = :userId")
    Optional<List<Location>> getLocationsByUserId(@Param("userId") UUID userId);
}
