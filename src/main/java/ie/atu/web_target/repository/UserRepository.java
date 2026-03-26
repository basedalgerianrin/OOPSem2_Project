package ie.atu.web_target.repository;

import ie.atu.web_target.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JpaRepository gives us free CRUD methods (save, findAll, findById, delete, etc.)
// We tell it: manage the User entity, and its ID type is Long
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring auto-generates: SELECT * FROM users WHERE username = ?
    // This is a SAFE parameterised query - used for lookups only
    User findByUsername(String username);
}
