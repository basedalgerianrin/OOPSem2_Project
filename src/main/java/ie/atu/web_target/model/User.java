package ie.atu.web_target.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    // Stored in plain text intentionally - vulnerable by design for IDS demo
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // e.g. "USER" or "ADMIN"
}
