package alessia.entities;

import alessia.entities.enums.TipoUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users")
public class User {
    @Id
    @Setter(AccessLevel.NONE)
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
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Activity> listOfFavouriteActivities = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Location> listOfFavouriteLocations = new ArrayList<>();
}
