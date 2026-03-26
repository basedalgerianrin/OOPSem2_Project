package ie.atu.web_target.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// @ControllerAdvice means this class watches ALL controllers for exceptions
// Instead of the app crashing with a white error page, we catch it and show
// a friendly message to the user
@ControllerAdvice
public class GlobalExceptionHandler {

    // Catches any unhandled exception anywhere in the app
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        // Pass the error message to the login page to display
        model.addAttribute("error", "An unexpected error occurred: " + ex.getMessage());
        return "login"; // Return to login page with the error shown
    }
}
