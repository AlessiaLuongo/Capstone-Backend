package alessia.controllers;

import alessia.exceptions.BadRequestException;
import alessia.payloads.NewUserDTO;
import alessia.payloads.NewUserResponseDTO;
import alessia.payloads.UserLoginResponseDTO;
import alessia.services.AuthenticationService;
import alessia.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UsersService usersService;


    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody NewUserDTO payload){
        return new UserLoginResponseDTO(this.authenticationService.authenticateUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponseDTO saveUser(@RequestBody @Validated NewUserDTO body, BindingResult validation){

        if(validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }

        return new NewUserResponseDTO(this.usersService.saveUser(body).getId());
    }
}
