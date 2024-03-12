package assignment.java.entity;

import assignment.java.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq",
            allocationSize = 1,initialValue = 3)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    Long id;

    @Column(name = "user_name", nullable = false)
    String username;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    Role role;

    @Column(name = "balance", nullable = false)
    @DecimalMin(value = "0.01")
    private BigDecimal balance = new BigDecimal(8.0);


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}