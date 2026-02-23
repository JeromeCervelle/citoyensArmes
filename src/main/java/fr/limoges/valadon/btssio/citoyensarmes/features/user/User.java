package fr.limoges.valadon.btssio.citoyensarmes.features.user;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Représente un utilisateur organisateur de tournois.
 */
@Data
@Document(collection = "users")
public class User implements UserDetails {
    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String password;
    private String name;

    private List<String> roles;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    // Un utilisateur organise un ou plusieurs tournois
    private List<String> tournamentIds;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) {
            return Collections.emptyList();
        }
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
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
