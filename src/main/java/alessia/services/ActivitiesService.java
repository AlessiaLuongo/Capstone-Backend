package alessia.services;

import alessia.entities.Activity;
import alessia.entities.User;
import alessia.entities.enums.EventType;
import alessia.exceptions.BadRequestException;
import alessia.payloads.NewActivityDTO;
import alessia.repositories.ActivitiesDAO;
import alessia.repositories.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@Service

public class ActivitiesService {
    @Autowired
    private ActivitiesDAO activitiesDAO;
    @Autowired
    private UsersDAO usersDAO;

    @Autowired
    private UsersService usersService;


    public Page<Activity> getAllActivities(int page, int size, String sortBy){
        if(size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.activitiesDAO.findAll(pageable);
    }

    public Activity saveActivity(NewActivityDTO body) throws BadRequestException {
        Activity newActivity = new Activity(
                body.title(),
                body.description(),
                LocalDate.now(),
                body.outdoor(),
                0,
                1,
                body.price(),
                new ArrayList<>(),
                this.usersDAO.findUserById(body.userId()).orElseThrow(),
                body.startDate(),
                body.endDate(),
                body.eventType()

        );
        return activitiesDAO.save(newActivity);
    }
}
