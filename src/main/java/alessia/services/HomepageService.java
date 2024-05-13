package alessia.services;

import alessia.entities.Activity;
import alessia.entities.Location;
import alessia.repositories.ActivitiesDAO;
import alessia.repositories.LocationsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomepageService {

    @Autowired
    private ActivitiesDAO activitiesDAO;

    @Autowired
    private LocationsDAO locationsDAO;


    public List<Activity> getTheBestActivities() {
        return this.activitiesDAO.getTheBestActivities();
    }

    public List<Location> getTheBestLocations() {
    return this.locationsDAO.getTheBestLocations();
    }

}
