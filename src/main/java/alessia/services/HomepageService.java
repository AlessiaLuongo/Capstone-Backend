package alessia.services;

import alessia.entities.Activity;
import alessia.entities.Location;
import alessia.repositories.ActivitiesDAO;
import alessia.repositories.LocationsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomepageService {

    @Autowired
    private ActivitiesDAO activitiesDAO;

    @Autowired
    private LocationsDAO locationsDAO;


    public List<Activity> getTheBestActivities() {
        List<Activity> allActivities = this.activitiesDAO.getTheBestActivities();
        return allActivities.subList(0, Math.min(10, allActivities.size()));
    }

    public List<Location> getTheBesLocations() {
        List<Location> allLocations = this.locationsDAO.getTheBestLocations();
        return allLocations.subList(0, Math.min(10, allLocations.size()));
    }


}
