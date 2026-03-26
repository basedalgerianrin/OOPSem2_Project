package ie.atu.web_target.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// This entity records every login attempt that hits the site
// The IDS will read these records to detect brute force and SQL injection attacks
@Data
@Entity
@Table(name = "login_attempts")
public class LoginAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The username that was submitted in the login form
    private String username;

    // The raw password input - stored so the IDS can inspect it for SQLi patterns
    private String passwordInput;

    // Whether the login succeeded or failed
    private boolean success;

    // Exact time the attempt was made - used by IDS to detect rapid repeated attempts
    private LocalDateTime timestamp;
}
