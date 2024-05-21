package alessia.services;

import alessia.entities.Activity;
import alessia.entities.Location;
import alessia.entities.User;
import alessia.entities.enums.TipoUser;
import alessia.exceptions.BadRequestException;
import alessia.exceptions.NotFoundException;
import alessia.payloads.NewUserDTO;
import alessia.repositories.ActivitiesDAO;
import alessia.repositories.LocationsDAO;
import alessia.repositories.UsersDAO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class UsersService {
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private Cloudinary cloudinaryUploader;

    @Autowired
    private ActivitiesDAO activitiesDAO;

    @Autowired
    private LocationsDAO locationsDAO;

    public Page<User> getAllUsers(int page, int size, String sortBy){
        if(size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.usersDAO.findAll(pageable);
    }

    public User saveUser(NewUserDTO body) throws BadRequestException {
        this.usersDAO.findUserByemail(body.email()).ifPresent(
                user -> {
            throw new BadRequestException("Email already exists");
        });
        User newUser = new User(
                body.username(),
                body.name(),
                body.surname(),
                body.email(),
                bcrypt.encode(body.password()),
                TipoUser.USER,
                "https://ui-avatars.com/api/?name="+ body.name() + "+" + body.surname(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        return this.usersDAO.save(newUser);

    }

    public User findUserById(UUID id) {
      return  this.usersDAO.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
    }

    public User findByIdAndUpdate(User user, User updatedUser) {
        //non serve l'id, l'ho impostato su ME
        user.setUsername(updatedUser.getUsername());
        user.setName(updatedUser.getName());
        user.setSurname(updatedUser.getSurname());
        return this.usersDAO.save(user);

    }

    public void deleteUserById(UUID id) {
        Optional<User> userOptional = this.usersDAO.findById(id);
        if (userOptional.isPresent()){
            User found = userOptional.get();
            this.usersDAO.delete(found);
        }else {
            throw new NotFoundException("User not found");
        }

    }

    public User findByemail(String email) {
        return this.usersDAO.findUserByemail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User uploadAvatar(User user, MultipartFile file)throws IOException {

        String avatarUrl = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        user.setAvatar(avatarUrl);
        return this.usersDAO.save(user);
    }

    public List<Activity> getFavouriteActivity(User user) {

        return this.usersDAO.getActivitiesByUserId(user.getId()).orElseThrow();
    }

    public User addFavouriteActivity(User user, UUID activityId) {
        Activity activity = this.activitiesDAO.findActivityById(activityId).orElseThrow(()->new NotFoundException("Activity not found"));
        List<User> users = activity.getUsers();
        List<Activity> activities = user.getActivities();
        users.add(user);
        activities.add(activity);
        this.usersDAO.save(user);
        this.activitiesDAO.save(activity);
        return this.usersDAO.findUserById(user.getId()).orElseThrow();
    }


@Transactional
    public void deleteFavouriteActivity(User user, UUID activityId) {
        Activity activity = this.activitiesDAO.findActivityById(activityId).orElseThrow(()->new NotFoundException("Activity not found"));
        ArrayList<User> users = new ArrayList<>(
                activity.getUsers().stream()
                        .filter(e-> !e.getId().equals(user.getId()))
                        .toList()
        );
        ArrayList<Activity> activities = new ArrayList<>(
                user.getActivities().stream()
                        .filter(e-> !e.getId().equals(activity.getId()))
                        .toList()
        );
        user.setActivities(activities);
        activity.setUsers(users);
        this.usersDAO.save(user);
        this.activitiesDAO.save(activity);

    }

    public List<Location> getFavouriteLocation(User user) {

        return this.usersDAO.getLocationsByUserId(user.getId()).orElseThrow();
    }

    public User addFavouriteLocation(User user, UUID locationId) {
       Location location = this.locationsDAO.findLocationById(locationId).orElseThrow(()->new NotFoundException("Location not found"));
        List<User> users = location.getUsers();
        List<Location> locations = user.getLocations();
        users.add(user);
       locations.add(location);
        this.usersDAO.save(user);
        this.locationsDAO.save(location);
        return this.usersDAO.findUserById(user.getId()).orElseThrow();
    }


    @Transactional
    public void deleteFavouriteLocation(User user, UUID locationId) {
        Location location = this.locationsDAO.findLocationById(locationId).orElseThrow(()->new NotFoundException("Activity not found"));
        ArrayList<User> users = new ArrayList<>(
                location.getUsers().stream()
                        .filter(e-> !e.getId().equals(user.getId()))
                        .toList()
        );
        ArrayList<Location> locations = new ArrayList<>(
                user.getLocations().stream()
                        .filter(e-> !e.getId().equals(location.getId()))
                        .toList()
        );
        user.setLocations(locations);
        location.setUsers(users);
        this.usersDAO.save(user);
        this.locationsDAO.save(location);

    }

}
