package assignment.java.controller;

import assignment.java.service.AuthenticateService;
import assignment.java.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final PaymentService paymentService;
    private final AuthenticateService authenticateService;

    @PostMapping("/payment")
    public ResponseEntity<String> payment(HttpServletRequest request) {
        if (paymentService.makePayment
                (authenticateService.extractUsernameFromToken(authenticateService.extractTokenFromRequest(request)))) {
            return ResponseEntity.ok("Payment successful!");
        } else {
            return ResponseEntity.badRequest().body("Insufficient funds or user not found");
        }
    }
}
