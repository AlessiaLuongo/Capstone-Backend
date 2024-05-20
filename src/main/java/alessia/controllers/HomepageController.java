package alessia.controllers;

import alessia.entities.Activity;
import alessia.entities.Location;
import alessia.entities.Post;
import alessia.repositories.ActivitiesDAO;
import alessia.repositories.LocationsDAO;
import alessia.services.ActivitiesService;
import alessia.services.HomepageService;
import alessia.services.LocationsService;
import org.hibernate.dialect.function.array.ArrayConcatElementFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/")
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
    public List<Object> getTheBestPosts(){

        List<Activity> activities = homepageService.getTheBestActivities();
        List<Location> locations = homepageService.getTheBestLocations();
        List<Object> posts = Stream.concat(activities.stream(), locations.stream())
                .sorted(Comparator.comparing(Post::getRate).reversed())
                .collect(Collectors.toList());




         return posts;
    }


}
