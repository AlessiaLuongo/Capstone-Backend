package alessia.services;

import alessia.entities.User;
import alessia.entities.enums.TipoUser;
import alessia.exceptions.BadRequestException;
import alessia.exceptions.NotFoundException;
import alessia.payloads.NewUserDTO;
import alessia.repositories.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service

public class UsersService {
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private PasswordEncoder bcrypt;

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
        return this.usersDAO.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
    }

    public User findByIdAndUpdate(UUID id, User updatedUser){
        Optional<User> userOptional = this.usersDAO.findById(id);
        if (userOptional.isPresent()){
        User found = userOptional.get();
        found.setUsername(updatedUser.getUsername());
        found.setName(updatedUser.getName());
        found.setSurname(updatedUser.getSurname());
        found.setEmail(updatedUser.getEmail());
        found.setPassword(bcrypt.encode(updatedUser.getPassword()));
        found.setAvatar(updatedUser.getAvatar());
        return this.usersDAO.save(found);
        }else {
            throw new NotFoundException("User not found");
        }

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
}