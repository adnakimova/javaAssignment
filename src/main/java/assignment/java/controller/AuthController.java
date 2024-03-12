package assignment.java.controller;

import assignment.java.config.security.TokenBlackList;
import assignment.java.dto.request.LoginRequest;
import assignment.java.dto.response.LoginResponse;
import assignment.java.service.AuthenticateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Auth", description = "The User Authentication API")
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticateService authenticateService;
    private final TokenBlackList tokenBlacklist;

    @Operation(summary = "User authentication", description = "This method to authenticate the user")
    @PostMapping("/login")
    public LoginResponse login (@RequestBody LoginRequest loginRequest){
        return authenticateService.authenticate(loginRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = authenticateService.extractTokenFromRequest(request);
        tokenBlacklist.addToBlacklist(token);

        return ResponseEntity.ok("Logged out successfully");
    }
}
