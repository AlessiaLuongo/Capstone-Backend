package alessia.payloads;
import alessia.entities.enums.EventType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;


public record NewActivityDTO(
        @NotEmpty(message = "A title is required")
        String title,
        @NotEmpty(message = "A description is required")
        String description,
        @DecimalMin(value = "0.0", message = "The price can not be under zero")
        double price,
        @FutureOrPresent(message = "The start date can not be in the past")
        LocalDate startDate,
        @Future(message = "The end date can not be in the past")
        LocalDate endDate,
        @Pattern(regexp = "CONCERT | SPORT| BUSINESS | CULTURAL | WORKSHOP | RELIGIOUS | OTHER", message = "Select a valid event type")
        EventType eventType,

        @Size(message = "Rating from 1 to 5", min = 1, max = 5)
        Integer rate,
        boolean outdoor,
        UUID userId
) {
}
