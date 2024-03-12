package assignment.java.dto.response;

import assignment.java.enums.Role;
import lombok.Builder;

@Builder
public record LoginResponse(
        String userName,
        Role role,
        String token
) {
}
