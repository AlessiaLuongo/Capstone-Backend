package alessia.payloads;
import jakarta.validation.constraints.*;


public record NewUserDTO(
        @NotEmpty(message = "Username is required")
        @Size(message = "Username must be between 4 and 16 characters", min = 4, max = 16)
        String username,
        @NotEmpty(message = "Name is required")
        String name,
        @NotEmpty(message = "Surname is required")
        String surname,
        @NotNull(message = "Email is required")
        @Email(message = "Email is not valid")
        String email,
        @NotNull(message = "Password is required")
        @Size(message = "Password must be between 4 and 16 characters", min = 4, max = 16)
        String password

) {
}
