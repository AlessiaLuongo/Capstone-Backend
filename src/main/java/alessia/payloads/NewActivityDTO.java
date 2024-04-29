package alessia.payloads;
import alessia.entities.enums.EventType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;


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
        EventType eventType
) {
}
