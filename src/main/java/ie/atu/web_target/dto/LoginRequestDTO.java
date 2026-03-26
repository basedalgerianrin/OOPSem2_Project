package ie.atu.web_target.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// DTO = Data Transfer Object
// This class carries the login form data from the browser to our Java code.
// It is NOT saved to the database - it just holds the data in transit.
@Data
public class LoginRequestDTO {

    // @NotBlank means this field cannot be empty or just whitespace
    // The message is what gets shown if validation fails
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
