package alessia.payloads;

import java.time.LocalDateTime;

public record ErrorsResponseDTO(String message, LocalDateTime time) {
}
