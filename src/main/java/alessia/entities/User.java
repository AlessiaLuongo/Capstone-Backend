package alessia.entities;

import alessia.entities.enums.TipoUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")

public class User implements UserDetails {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;
    @Column(name = "tipo_user")
    @Enumerated(EnumType.STRING)
    private TipoUser tipoUser;
    private String avatar;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Activity> activities;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Location> locations;


    public User(String username, String name, String surname, String email, String password, TipoUser tipoUser, String avatar, List<Activity> listOfFavouriteActivities, List<Location> listOfFavouriteLocations) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.tipoUser = TipoUser.USER;
        this.avatar = avatar;
        this.activities = listOfFavouriteActivities;
        this.locations = listOfFavouriteLocations;
}


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
