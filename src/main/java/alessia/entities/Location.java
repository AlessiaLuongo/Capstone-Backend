package alessia.entities;

import alessia.entities.enums.InfluxOfPeople;
import alessia.entities.enums.LocationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    private LocationType locationType;
    @Column(name = "influx_of_people")
    private InfluxOfPeople influxOfPeople;


}
