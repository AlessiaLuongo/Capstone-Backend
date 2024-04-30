package alessia.services;

import alessia.entities.User;
import alessia.exceptions.UnauthorizedException;
import alessia.payloads.NewUserDTO;
import alessia.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UsersService usersService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUserAndGenerateToken(NewUserDTO payload){
        User user = this.usersService.findByemail(payload.email());

        if (bcrypt.matches(payload.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Invalid credentials");
        }


    }
}
