package alessia.controllers;

import alessia.entities.Activity;
import alessia.entities.Location;
import alessia.entities.User;
import alessia.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin

public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "username")String sortBy) {
        return this.usersService.getAllUsers(page, size, sortBy);
    }

    @GetMapping("/{userId}")
    public User findById(@PathVariable UUID userId){
        return this.usersService.findUserById(userId);
    }

    @PutMapping("/me")

    public User findByIdAndUpdate(@AuthenticationPrincipal User user, @RequestBody User body){
        return this.usersService.findByIdAndUpdate(user, body);
    }

    @DeleteMapping("/{userId}")

    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID userId){
        this.usersService.deleteUserById(userId);
    }

    @GetMapping("/me")
    public User getMyProfile(@AuthenticationPrincipal User user){
        return user;
    }


    @PatchMapping("/me/avatar")
    public User uploadAvatar(@RequestParam("avatar") MultipartFile file, @AuthenticationPrincipal User user) throws IOException {
        try {
            return usersService.uploadAvatar(user, file);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/me/favourite-activities")
    public List<Activity> getFavouriteActivity (@AuthenticationPrincipal User user){
        return usersService.getFavouriteActivity(user);
    }

    @PatchMapping("/me/favourite-activities/{activityId}")
    public User addFavouriteActivity (@AuthenticationPrincipal User user, @PathVariable UUID activityId){
        return usersService.addFavouriteActivity(user, activityId);
    }

    @DeleteMapping("/me/favourite-activities/{activityId}")
    public void deleteFavouriteActivity(@AuthenticationPrincipal User user, @PathVariable UUID activityId) {
        usersService.deleteFavouriteActivity(user, activityId);
    }


    @GetMapping("/me/favourite-locations")
    public List<Location> getFavouriteLocations (@AuthenticationPrincipal User user){
        return usersService.getFavouriteLocation(user);
    }

    @PatchMapping("/me/favourite-locations/{locationId}")
    public User addFavouriteLocation (@AuthenticationPrincipal User user, @PathVariable UUID locationId){
        return usersService.addFavouriteLocation(user, locationId);
    }

    @DeleteMapping("/me/favourite-locations/{locationId}")
    public void deleteFavouriteLocation(@AuthenticationPrincipal User user, @PathVariable UUID locationId) {
        usersService.deleteFavouriteLocation(user, locationId);
    }




}
