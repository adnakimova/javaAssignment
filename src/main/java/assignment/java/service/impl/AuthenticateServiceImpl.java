package assignment.java.service.impl;

import assignment.java.config.jwt.JwtService;
import assignment.java.dto.request.LoginRequest;
import assignment.java.dto.response.LoginResponse;
import assignment.java.entity.User;
import assignment.java.exception.UnauthorizedException;
import assignment.java.repository.UserRepository;
import assignment.java.service.AuthenticateService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthenticateServiceImpl implements AuthenticateService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public LoginResponse authenticate(LoginRequest request) {

        User userInfo = userRepository.findByUsername(request.userName())
                .orElseThrow(() -> {
                    log.error(String.format("Пользователь с адресом электронной почты %s не существует", request.userName()));
                    return new BadCredentialsException(String.format("Пользователь с адресом электронной почты %s не существует", request.userName()));
                });
        if (!passwordEncoder.matches(request.password(), userInfo.getPassword())) {
            log.error("Пароль не подходит");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.userName(),
                        request.password()
                )
        );
        log.info(String.format("Пользователь %s успешно аутентифицирован", userInfo.getUsername()));
        String token = jwtService.generateToken(userInfo);

        return LoginResponse.builder()
                .userName(userInfo.getUsername())
                .role(userInfo.getRole())
                .token(token)
                .build();
    }

    @Override
    public String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new UnauthorizedException("Unauthorized");
    }

    @Override
    public String extractUsernameFromToken(String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Unauthorized");
        }
        return authentication.getName();
    }


}