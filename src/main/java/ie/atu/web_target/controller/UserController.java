package ie.atu.web_target.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ie.atu.web_target.service.UserService;
import jakarta.validation.Valid;
import ie.atu.web_target.dto.UserResponseDTO;
import ie.atu.web_target.dto.UserRequestDTO;
import org.springframework.http.HttpStatus;


import java.util.List;

// @RestController = @Controller + automatically returns JSON (not HTML pages)
// This is what Swagger documents and what the IDS will call
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // GET /api/users - returns all users in the database as JSON
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET /api/users/{id} - returns a single user by their ID
    // ResponseEntity lets us return a proper 404 if the user doesn't exist
    @GetMapping("/{id}")
    public  ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO created = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO request)
    {
        UserResponseDTO updated = userService.updateUser(id, request);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
