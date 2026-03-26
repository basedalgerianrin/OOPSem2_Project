package ie.atu.web_target.controller;

import ie.atu.web_target.model.LoginAttempt;
import ie.atu.web_target.repository.LoginAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST controller that exposes login attempt data
// The IDS service will call these endpoints to monitor suspicious activity
@RestController
@RequestMapping("/api/attempts")
@RequiredArgsConstructor
public class LoginAttemptController {

    private final LoginAttemptRepository loginAttemptRepository;

    // GET /api/attempts - returns ALL login attempts (success + failed)
    @GetMapping
    public List<LoginAttempt> getAllAttempts() {
        return loginAttemptRepository.findAll();
    }

    // GET /api/attempts/failed - returns only failed attempts
    // IDS uses this to detect brute force (many failures in a short time)
    @GetMapping("/failed")
    public List<LoginAttempt> getFailedAttempts() {
        return loginAttemptRepository.findBySuccess(false);
    }

    // GET /api/attempts/user/{username} - returns all attempts for one username
    // IDS uses this to spot targeted attacks on a specific account
    @GetMapping("/user/{username}")
    public List<LoginAttempt> getAttemptsByUser(@PathVariable String username) {
        return loginAttemptRepository.findByUsername(username);
    }
}
