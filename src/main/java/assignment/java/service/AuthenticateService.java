package assignment.java.service;

import assignment.java.dto.request.LoginRequest;
import assignment.java.dto.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticateService {
    LoginResponse authenticate (LoginRequest loginRequest);
    String extractTokenFromRequest (HttpServletRequest request);
    String extractUsernameFromToken (String token);
}