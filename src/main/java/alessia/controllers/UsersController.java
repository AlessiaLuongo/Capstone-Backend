package alessia.controllers;

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

    @PutMapping("/{userId}")
   // @PreAuthorize("hasAuthority('ADMIN')")
    public User findByIdAndUpdate(@PathVariable UUID userId, @RequestBody User body){
        return this.usersService.findByIdAndUpdate(userId, body);
    }

    @DeleteMapping("/{userId}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID userId){
        this.usersService.deleteUserById(userId);
    }

    @GetMapping("/me")
    public User getMyProfile(@AuthenticationPrincipal User user){
        return user;
    }


    @PatchMapping("/{userId}/avatar")
    public User uploadAvatar(@RequestParam("avatar") MultipartFile file, @PathVariable UUID userId){
        try {
            return usersService.uploadAvatar(userId, file);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
