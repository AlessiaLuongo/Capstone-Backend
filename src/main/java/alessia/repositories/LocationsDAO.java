package alessia.repositories;

import alessia.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocationsDAO extends JpaRepository<Location, UUID> {
    Optional<Location> findLocationById(UUID id);

    Optional<Location> findLocationByTitle(String title);

    @Query(name = "getTheBestLocations")
    List<Location> getTheBestLocations();
}
