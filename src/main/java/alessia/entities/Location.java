package alessia.entities;

import alessia.entities.enums.InfluxOfPeople;
import alessia.entities.enums.LocationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
