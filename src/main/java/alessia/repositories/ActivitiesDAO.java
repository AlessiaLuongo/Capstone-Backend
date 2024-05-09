package alessia.repositories;

import alessia.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ActivitiesDAO extends JpaRepository<Activity, UUID> {
    Optional<Activity> findActivityById(UUID id);
    Optional<Activity> findActivityByTitle(String title);

    @Query(name = "getTheBestActivities")
    List<Activity> getTheBestActivities();

}
