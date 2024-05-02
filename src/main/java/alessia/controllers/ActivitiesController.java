package alessia.controllers;

import alessia.entities.Activity;
import alessia.entities.User;
import alessia.exceptions.BadRequestException;
import alessia.payloads.NewActivityDTO;
import alessia.services.ActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/activities")

public class ActivitiesController {

    @Autowired
    private ActivitiesService activitiesService;

    @PostMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
    public Activity saveActivity(@AuthenticationPrincipal User currentUser, @RequestBody NewActivityDTO body) throws BadRequestException {
        return this.activitiesService.saveActivity(currentUser, body);
    }

    @GetMapping
    public Page<Activity> getAllActivities(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "username")String sortBy) {
        return this.activitiesService.getAllActivities(page, size, sortBy);
    }

    @GetMapping("/{activityId}")
    public Activity findById(@PathVariable UUID activityId){
        return this.activitiesService.findActivityById(activityId);
    }

    @PutMapping("/{activityId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Activity findActivityByIdAndUpdate(@PathVariable UUID activityId, @RequestBody Activity activity) {
        return this.activitiesService.findActivityByIdAndUpdate(activityId, activity);
    }

    @DeleteMapping("/{activityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findActivityByIdAndDelete(@PathVariable UUID activityId) {
        this.activitiesService.deleteActivityById(activityId);
    }
}
