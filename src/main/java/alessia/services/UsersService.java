package alessia.services;

import alessia.entities.User;
import alessia.entities.enums.TipoUser;
import alessia.exceptions.BadRequestException;
import alessia.exceptions.NotFoundException;
import alessia.payloads.NewUserDTO;
import alessia.repositories.UsersDAO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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
}
