package ie.atu.web_target.service;

import ie.atu.web_target.dto.LoginRequestDTO;
import ie.atu.web_target.model.LoginAttempt;
import ie.atu.web_target.model.User;
import ie.atu.web_target.repository.LoginAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JdbcTemplate jdbcTemplate;
    private final LoginAttemptRepository loginAttemptRepository;

    // ---------------------------------------------------------------
    // VULNERABLE login - intentionally unsafe for IDS demo
    // Directly concatenates user input into the SQL string.
    //
    // Attack example:
    //   username: ' OR '1'='1' --
    //   password: anything
    // This bypasses the password check entirely.
    // ---------------------------------------------------------------
    public User vulnerableLogin(LoginRequestDTO request) {

        // Raw SQL built from user input - vulnerable to SQL injection
        String sql = "SELECT * FROM users WHERE username = '"
                + request.getUsername()
                + "' AND password = '"
                + request.getPassword() + "'";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        boolean success = !rows.isEmpty();

        // Record every attempt to the database so the IDS can monitor it
        saveLoginAttempt(request.getUsername(), request.getPassword(), success);

        if (success) {
            Map<String, Object> row = rows.get(0);
            User user = new User();
            user.setId(((Number) row.get("ID")).longValue());
            user.setUsername((String) row.get("USERNAME"));
            user.setPassword((String) row.get("PASSWORD"));
            user.setRole((String) row.get("ROLE"));
            return user;
        }

        return null;
    }

    // Saves a record of the login attempt to the login_attempts table
    private void saveLoginAttempt(String username, String passwordInput, boolean success) {
        LoginAttempt attempt = new LoginAttempt();
        attempt.setUsername(username);
        attempt.setPasswordInput(passwordInput);  // raw input stored for IDS inspection
        attempt.setSuccess(success);
        attempt.setTimestamp(LocalDateTime.now());
        loginAttemptRepository.save(attempt);
    }
}
