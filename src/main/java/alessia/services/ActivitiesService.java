package alessia.services;

import alessia.entities.Activity;
import alessia.entities.User;
import alessia.exceptions.NotFoundException;
import alessia.payloads.NewActivityDTO;
import alessia.repositories.ActivitiesDAO;
import alessia.repositories.UsersDAO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ActivitiesService {
    @Autowired
    private ActivitiesDAO activitiesDAO;
    @Autowired
    private UsersDAO usersDAO;

    @Autowired
    private UsersService usersService;

    @Autowired
    private Cloudinary cloudinaryUploader;


    public Page<Activity> getAllActivities(int page, int size, String sortBy){
        if(size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.activitiesDAO.findAll(pageable);
    }





    public Activity saveActivity(User currentUser, NewActivityDTO body)  {


        Activity newActivity = new Activity(
                body.title(),
                body.description(),
                LocalDate.now(),
                body.outdoor(),
                0,
                body.rate(),
                body.price(),
                body.picture(),
                currentUser,
                body.startDate(),
                body.endDate(),
                body.eventType(),
                new ArrayList<>()
        );
                return activitiesDAO.save(newActivity);

    }

    public Activity findActivityById(UUID activityId){
        return activitiesDAO.findActivityById(activityId).orElseThrow(()-> new NotFoundException("Activity not found"));
    }

    public Activity findActivityByIdAndUpdate(UUID activityId, Activity updatedActivity) {
        Optional<Activity> optionalActivity = activitiesDAO.findActivityById(activityId);
        if (optionalActivity.isPresent()){
            Activity found = optionalActivity.get();
            found.setTitle(updatedActivity.getTitle());
            found.setDescription(updatedActivity.getDescription());
            found.setOutdoor(updatedActivity.isOutdoor());
            found.setNumberOfVisits(updatedActivity.getNumberOfVisits());
            found.setRate(updatedActivity.getRate());
            found.setPrice(updatedActivity.getPrice());

            found.setStartDate(updatedActivity.getStartDate());
            found.setEndDate(updatedActivity.getEndDate());
            found.setEventType(updatedActivity.getEventType());


            return activitiesDAO.save(found);

        }else {
            throw new NotFoundException("Activity not found");
        }
    }


    public void findActivityByIdAndDelete(UUID activityId) {
        Optional<Activity> optionalActivity = activitiesDAO.findActivityById(activityId);
        if (optionalActivity.isPresent()) {
            Activity found = optionalActivity.get();

            activitiesDAO.delete(found);
        } else {
            throw new NotFoundException("Activity not found");
        }
    }

    public Activity uploadActivityPicture(UUID activityId, MultipartFile file)throws IOException {
        Activity found = findActivityById(activityId);

            String pictureUrl = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
            found.setPicture(pictureUrl);


        return this.activitiesDAO.save(found);
    }

}
