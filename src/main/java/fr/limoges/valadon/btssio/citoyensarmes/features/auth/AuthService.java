package fr.limoges.valadon.btssio.citoyensarmes.features.auth;

import fr.limoges.valadon.btssio.citoyensarmes.features.user.User;
import fr.limoges.valadon.btssio.citoyensarmes.features.user.UserRole;
import fr.limoges.valadon.btssio.citoyensarmes.features.user.UserRepository;
import fr.limoges.valadon.btssio.citoyensarmes.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import fr.limoges.valadon.btssio.citoyensarmes.features.log.ConnectionLog;
import fr.limoges.valadon.btssio.citoyensarmes.features.log.ConnectionLogRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConnectionLogRepository connectionLogRepository;

    public AuthResponse register(RegisterRequest request) {
        var user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setRole(UserRole.ADMIN);
        user.setStatus(fr.limoges.valadon.btssio.citoyensarmes.features.user.UserStatus.ACTIVE);
        user.setTournamentIds(new ArrayList<>());
        
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Un compte existe déjà avec cet e-mail");
        }
        
        userRepository.save(user);
        
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpirationTime())
                .build();
    }

    public AuthResponse login(LoginRequest request, HttpServletRequest httpRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        String ipAddress = null;
        if (httpRequest != null) {
            ipAddress = httpRequest.getHeader("X-Forwarded-For");
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = httpRequest.getRemoteAddr();
            } else {
                ipAddress = ipAddress.split(",")[0].trim();
            }
        }

        // Création et sauvegarde du log de connexion
        var log = ConnectionLog.builder()
                .userId(user.getId())
                .connectionDateTime(LocalDateTime.now())
                .ipAddress(ipAddress)
                .build();
        connectionLogRepository.save(log);

        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpirationTime())
                .build();
    }
}
