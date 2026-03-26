package ie.atu.web_target.controller;

import ie.atu.web_target.dto.LoginRequestDTO;
import ie.atu.web_target.model.User;
import ie.atu.web_target.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// @Controller (not @RestController) means this returns HTML pages, not JSON
// @RequiredArgsConstructor auto-generates a constructor injecting AuthService
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;

    // ── GET /login ──
    // Shows the login page when the user visits the URL
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        // We pass an empty LoginRequestDTO to the form so Thymeleaf can bind it
        model.addAttribute("loginRequest", new LoginRequestDTO());
        return "login"; // → tells Thymeleaf to render templates/login.html
    }

    // ── GET / ──
    // Redirect root URL to /login so the site opens on the login page
    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    // ── GET /account ──
    // Shows the account dashboard after a successful login
    // @RequestParam pulls the ?user= value from the URL
    @GetMapping("/account")
    public String showAccountPage(@RequestParam(name = "user") String username, Model model) {
        model.addAttribute("username", username);
        return "account"; // → renders templates/account.html
    }

    // ── GET /logout ──
    // Logs the user out by redirecting back to login
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    // ── POST /login ──
    // Handles the form submission when user clicks Sign In
    // @Valid triggers Jakarta Validation on the DTO fields
    // BindingResult holds any validation errors (e.g. blank username)
    @PostMapping("/login")
    public String processLogin(
            @Valid @ModelAttribute("loginRequest") LoginRequestDTO request,
            BindingResult bindingResult,
            Model model) {

        // If validation failed (e.g. empty fields), go back to login with error
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .findFirst()
                    .orElse("Invalid input");
            model.addAttribute("error", errorMsg);
            return "login";
        }

        // Call the vulnerable login method in AuthService
        User user = authService.vulnerableLogin(request);

        if (user != null) {
            // Login succeeded - store username in the model and redirect to account page
            model.addAttribute("username", user.getUsername());
            model.addAttribute("role", user.getRole());
            return "redirect:/account?user=" + user.getUsername();
        } else {
            // Login failed - show error message
            model.addAttribute("error", "Login failed. Invalid username or password.");
            return "login";
        }
    }
}
