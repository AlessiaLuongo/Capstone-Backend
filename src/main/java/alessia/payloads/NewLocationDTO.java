package alessia.payloads;

import alessia.entities.enums.InfluxOfPeople;
import alessia.entities.enums.LocationType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

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
        boolean outdoor,
        UUID userId
) {
}
