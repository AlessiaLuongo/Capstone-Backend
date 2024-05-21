package alessia.entities;

import alessia.entities.enums.InfluxOfPeople;
import alessia.entities.enums.LocationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "locations")


public class Location extends Post{
    @Column(name = "location_type")
    @Enumerated(EnumType.STRING)
    private LocationType locationType;
    @Column(name = "influx_of_people")
    @Enumerated(EnumType.STRING)
    private InfluxOfPeople influxOfPeople;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    List<User> users;

    public Location(String title, String description, LocalDate creationDate, boolean outdoor, long numberOfVisits, int rate, double price, String picture, User user, LocationType locationType, InfluxOfPeople influxOfPeople, List<User> users) {
        super(title, description, creationDate, outdoor, numberOfVisits, rate, price, picture);
        this.locationType = locationType;
        this.influxOfPeople = influxOfPeople;
        this.user = user;
    }
}
