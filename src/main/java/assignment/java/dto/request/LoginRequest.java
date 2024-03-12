package assignment.java.dto.request;

import assignment.java.validation.PasswordValid;
import lombok.Builder;

@Builder
public record LoginRequest(
        String userName,
        @PasswordValid
        String password
) {
}
