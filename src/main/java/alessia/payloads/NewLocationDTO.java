package alessia.payloads;

import alessia.entities.enums.InfluxOfPeople;
import alessia.entities.enums.LocationType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record NewLocationDTO(
        @NotEmpty(message = "A title is required")
        String title,
        @NotEmpty(message = "A description is required")
        String description,
        @DecimalMin(value = "0.0", message = "The price can not be under zero")
        double price,
        @Pattern(regexp = "NATURALISTIC | CULTURAL | HISTORICAL | SPORT | EATERY | RELIGIOUS | OTHER", message = "Select a valid location type")
        LocationType locationType,
        @Pattern(regexp = "LOW, MEDIUM, HIGH", message = "Select a valid influx of people")
        InfluxOfPeople influxOfPeople,
        @Size(message = "Rating from 1 to 5", min = 1, max = 5)
        Integer rate,
        boolean outdoor,
        UUID userId,
        String picture
) {

}
