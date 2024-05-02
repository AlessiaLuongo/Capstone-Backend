package alessia.repositories;

import alessia.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LocationsDAO extends JpaRepository<Location, UUID> {
    Optional<Location> findLocationById(UUID id);

    Optional<Location> findLocationByTitle(String title);
}
