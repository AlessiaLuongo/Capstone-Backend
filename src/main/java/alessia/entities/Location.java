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
@NamedQuery(name = "getTheBestLocations", query = "SELECT l FROM Location l ORDER BY l.rate DESC")

public class Location extends Post{
    @Column(name = "location_type")
    @Enumerated(EnumType.STRING)
    private LocationType locationType;
    @Column(name = "influx_of_people")
    @Enumerated(EnumType.STRING)
    private InfluxOfPeople influxOfPeople;


    public Location(String title, String description, LocalDate creationDate, boolean outdoor, long numberOfVisits, int rate, double price, List<String> listOfPictures, User user, LocationType locationType, InfluxOfPeople influxOfPeople) {
        super(title, description, creationDate, outdoor, numberOfVisits, rate, price, listOfPictures, user);
        this.locationType = locationType;
        this.influxOfPeople = influxOfPeople;
    }
}
