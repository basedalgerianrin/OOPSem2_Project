package ie.atu.web_target.repository;

import ie.atu.web_target.model.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {

    // Find all attempts for a specific username
    // Useful for IDS to check how many times one user has failed
    List<LoginAttempt> findByUsername(String username);

    // Find all failed attempts - IDS uses this to spot brute force
    List<LoginAttempt> findBySuccess(boolean success);
}
