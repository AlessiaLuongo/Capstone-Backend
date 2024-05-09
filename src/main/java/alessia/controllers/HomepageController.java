package alessia.controllers;

import alessia.entities.Activity;
import alessia.repositories.ActivitiesDAO;
import alessia.services.ActivitiesService;
import alessia.services.HomepageService;
import alessia.services.LocationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/homepage")
@CrossOrigin(origins = "http://localhost:5173")
public class HomepageController {

    @Autowired
    private HomepageService homepageService;

    @Autowired
    private ActivitiesService activitiesService;

    @Autowired
    private ActivitiesDAO activitiesDAO;

    @GetMapping
    @PreAuthorize("permitAll")
    public List<Activity> getTheBestActivities(){
        return this.homepageService.getTheBestActivities();
    }

}
