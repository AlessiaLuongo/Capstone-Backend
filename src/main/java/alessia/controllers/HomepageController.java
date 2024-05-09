package alessia.controllers;

import alessia.entities.Activity;
import alessia.entities.Location;
import alessia.repositories.ActivitiesDAO;
import alessia.repositories.LocationsDAO;
import alessia.services.ActivitiesService;
import alessia.services.HomepageService;
import alessia.services.LocationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
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

    @Autowired
    private LocationsService locationsService;
    @Autowired
    private LocationsDAO locationsDAO;

    @GetMapping
    @PreAuthorize("permitAll")
    public HashSet<Object> getTheBestPosts(){
        HashSet<Object> posts = new HashSet<>();
        posts.add( this.homepageService.getTheBestActivities());
        posts.add(this.homepageService.getTheBesLocations());

         return posts;
    }


}
